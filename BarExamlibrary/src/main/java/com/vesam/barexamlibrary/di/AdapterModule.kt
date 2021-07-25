package com.vesam.barexamlibrary.di

import com.vesam.barexamlibrary.ui.view.adapter.answer_list.AnswerAdapter
import com.vesam.barexamlibrary.ui.view.adapter.category_list.CategoryAdapter
import com.vesam.barexamlibrary.ui.view.adapter.download_file_list.DownloadFileAdapter
import com.vesam.barexamlibrary.ui.view.adapter.test_list.TestAdapter
import com.vesam.barexamlibrary.ui.view.adapter.slider_list.AdapterImageSlider
import org.koin.dsl.module

val adapterModule = module {

    single { return@single CategoryAdapter(get(),get()) }
    single { return@single TestAdapter(get(),get()) }
    single { return@single AdapterImageSlider(get(),get()) }
    single { return@single AnswerAdapter(get()) }
    single { return@single DownloadFileAdapter(get()) }
}