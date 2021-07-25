package com.vesam.barexamlibrary.di

import com.vesam.barexamlibrary.ui.viewmodel.QuizViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { QuizViewModel(get()) }
}