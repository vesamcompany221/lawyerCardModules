package com.vesam.barexamlibrary.di

import android.content.Context
import android.media.MediaPlayer
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import com.vesam.barexamlibrary.BuildConfig
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.api.ApiHelper
import com.vesam.barexamlibrary.data.api.ApiHelperImpl
import com.vesam.barexamlibrary.data.api.ApiService
import com.vesam.barexamlibrary.utils.application.AppQuiz.Companion.activity
import com.vesam.barexamlibrary.utils.application.AppQuiz.Companion.context
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.APPLICATION_JSON_HEADER
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.BASE_URL
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.CONTENT_TYPE_HEADER
import com.vesam.barexamlibrary.utils.image.ZoomOutSlideTransformer
import com.vesam.barexamlibrary.utils.manager.DialogManager
import com.vesam.barexamlibrary.utils.manager.GridLayoutCountManager
import com.vesam.barexamlibrary.utils.manager.KeyboardManager
import com.vesam.barexamlibrary.utils.music_manager.BeatBox
import com.vesam.barexamlibrary.utils.network_helper.NetworkHelper
import com.vesam.barexamlibrary.utils.tools.*
import com.vesam.barexamlibrary.utils.type_converters.ToStringConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { initNavController() }
    single { provideRetrofit(get(),get()) }
    single { provideNetworkHelper(androidContext()) }
    single<ApiHelper> { return@single ApiHelperImpl(get()) }
    single { return@single ThrowableTools(get(),get()) }
    single { return@single Gson() }
    single { return@single ToastTools() }
    single { return@single GlideTools(get(), get()) }
    single { return@single HandelErrorTools() }
    single { return@single GridLayoutCountManager(get()) }
    single { return@single ToStringConverterFactory() }
    single { return@single KeyboardManager() }
    single { return@single NetworkTools() }
    single { return@single DialogManager(get(),get()) }
    single { return@single BottomSheetDialog(activity) }
    single { return@single ZoomOutSlideTransformer() }
    single { return@single BeatBox(get(),get()) }
    single { return@single MediaPlayer() }
    single { provideOkHttpClient() }
}

private fun initNavController() =
    Navigation.findNavController(activity, R.id.my_nav_fragment)

fun provideNetworkHelper(context: Context) = NetworkHelper(context)

fun provideOkHttpClient(): OkHttpClient {
    val i = Interceptor { chain: Interceptor.Chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.addHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON_HEADER)
        val request: Request = requestBuilder.build()
        val response = chain.proceed(request)
        response
    }
    val okHttpClient= OkHttpClient.Builder().writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).addInterceptor(i);
    if (BuildConfig.DEBUG) {
        okHttpClient.addInterceptor(ChuckInterceptor(context))
    }
    return okHttpClient.build()
}

fun provideRetrofit(okHttpClient: OkHttpClient, toStringConverterFactory: ToStringConverterFactory): ApiService =
    Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(toStringConverterFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)
