package com.vesam.barexamlibrary.utils.option

import android.app.Activity
import android.content.Intent
import com.vesam.barexamlibrary.ui.view.activity.QuizActivity
import com.vesam.barexamlibrary.utils.build_config.BuildConfig
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.BASE_URL
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_API_TOKEN_VALUE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_UUID_VALUE

class Option{

    companion object{
        @JvmStatic
        fun start(activity: Activity,
                  baseUrl: String,
                  token: String,
                  userId: String) {
            BASE_URL = baseUrl
            USER_API_TOKEN_VALUE = token
            USER_UUID_VALUE = userId
            val intent = Intent()
            intent.setClass(activity, QuizActivity::class.java)
            activity.startActivity(intent)
        }
    }


}