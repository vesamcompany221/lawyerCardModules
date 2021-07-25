package com.vesam.barexamlibrary.data.api

import com.vesam.barexamlibrary.data.model.response.buy_wallet.ResponseBuyWalletModel
import com.vesam.barexamlibrary.data.model.response.get_category_detail.ResponseGetCategoryDetailModel
import com.vesam.barexamlibrary.data.model.response.get_category_list.ResponseGetCategoryListModel
import com.vesam.barexamlibrary.data.model.response.get_quiz_question.ResponseGetQuizQuestionModel
import com.vesam.barexamlibrary.data.model.response.online_payment.ResponseOnlinePaymentModel
import com.vesam.barexamlibrary.data.model.response.set_quiz_question.ResponseSetQuizQuestionModel
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.API_TOKEN
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.BUY_WALLET
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.CATEGORY_ID
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.GET_CATEGORY_DETAIL
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.GET_CATEGORY_LIST
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.GET_QUIZ_QUESTION
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.GET_QUIZ_RESULT
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.ONLINE_PAYMENT
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.QUESTION_ID
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.QUIZ_ID
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.SET_QUIZ_QUESTION
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_ANSWER
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_API_TOKEN
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_UUID
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.UUID
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(GET_CATEGORY_LIST)
    suspend fun initGetCategoryList(
        @Header(USER_UUID) userUuid: String,
        @Header(USER_API_TOKEN) userApiToken: String,
    ): ResponseGetCategoryListModel

    @POST(GET_CATEGORY_LIST)
    @FormUrlEncoded
    suspend fun initGetCategoryList(
        @Header(USER_UUID) userUuid: String,
        @Header(USER_API_TOKEN) userApiToken: String,
        @Field(CATEGORY_ID) categoryId: Int
    ): ResponseGetCategoryListModel

    @POST(GET_CATEGORY_DETAIL)
    @FormUrlEncoded
    suspend fun initGetCategoryDetail(
        @Header(USER_UUID) userUuid: String,
        @Header(USER_API_TOKEN) userApiToken: String,
        @Field(QUIZ_ID) quizId: Int
    ): ResponseGetCategoryDetailModel

    @POST(GET_QUIZ_QUESTION)
    @FormUrlEncoded
    suspend fun initGetQuizQuestion(
        @Header(USER_UUID) userUuid: String,
        @Header(USER_API_TOKEN) userApiToken: String,
        @Field(QUIZ_ID) quizId: Int,
        @Field(QUESTION_ID) questionId: Int
    ): ResponseGetQuizQuestionModel

    @POST(SET_QUIZ_QUESTION)
    @FormUrlEncoded
    suspend fun initSetQuizQuestion(
        @Header(USER_UUID) userUuid: String,
        @Header(USER_API_TOKEN) userApiToken: String,
        @Field(QUIZ_ID) quizId: Int,
        @Field(USER_ANSWER) userAnswers: String
    ): ResponseSetQuizQuestionModel

    @POST(GET_QUIZ_RESULT)
    @FormUrlEncoded
    suspend fun initGetQuizQuestionResult(
        @Header(USER_UUID) userUuid: String,
        @Header(USER_API_TOKEN) userApiToken: String,
        @Field(QUIZ_ID) quizId: Int
    ): ResponseSetQuizQuestionModel

    @POST(ONLINE_PAYMENT)
    @FormUrlEncoded
    suspend fun initOnlinePayment(
        @Field(UUID) userUuid: String,
        @Field(API_TOKEN) userApiToken: String,
        @Field(QUIZ_ID) quizId: Int
    ): ResponseOnlinePaymentModel

    @POST(BUY_WALLET)
    @FormUrlEncoded
    suspend fun initBuyWallet(
        @Field(UUID) userUuid: String,
        @Field(API_TOKEN) userApiToken: String,
        @Field(QUIZ_ID) quizId: Int
    ): ResponseBuyWalletModel



}