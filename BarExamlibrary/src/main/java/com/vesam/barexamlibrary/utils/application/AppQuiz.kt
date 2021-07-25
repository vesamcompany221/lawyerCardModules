package com.vesam.barexamlibrary.utils.application

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.di.appModule
import com.vesam.barexamlibrary.di.DatabaseModule
import com.vesam.barexamlibrary.di.adapterModule
import com.vesam.barexamlibrary.di.repoModule
import com.vesam.barexamlibrary.di.viewModelModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppQuiz : MultiDexApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        @SuppressLint("StaticFieldLeak")
        lateinit var activity: Activity
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }

    fun init(context: Context) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        AppQuiz.context = context
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/iransansmobile.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
        startKoin {
            androidContext(context)
            modules(listOf(appModule, adapterModule, DatabaseModule, repoModule, viewModelModule))
        }
    }
}