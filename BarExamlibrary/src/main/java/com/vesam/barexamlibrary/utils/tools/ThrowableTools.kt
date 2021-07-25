package com.vesam.barexamlibrary.utils.tools

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.response.errorMessage.ResponseErrorMessageModel
import com.vesam.barexamlibrary.utils.application.AppQuiz
import retrofit2.HttpException
import java.net.SocketTimeoutException


class ThrowableTools(private val networkTools: NetworkTools,private val gson: Gson) {

    fun getThrowableError(throwable: Throwable): String {
        return initResultException(throwable)
    }

    private fun initResultException(throwable: Throwable): String {
        return when {
            networkTools.isNetworkAvailable -> AppQuiz.context.getString(R.string.no_connection)
            throwable is HttpException -> initHttpException(throwable)
            throwable is SocketTimeoutException -> AppQuiz.context.getString(R.string.time_out)
            else -> throwable.message.toString()
        }
    }

    private fun initHttpException(throwable: Throwable): String {
        val responseBody = (throwable as HttpException).response()!!.errorBody()
        return try {
            val type = object : TypeToken<ResponseErrorMessageModel>() {}.type
            val responseErrorResponse: ResponseErrorMessageModel? =
                gson.fromJson(responseBody!!.charStream(), type)
            return responseErrorResponse?.error?.message.toString()
        } catch (e: Exception) {
            throwable.message()
        }
    }
}