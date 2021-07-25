package com.vesam.barexamlibrary.di

import com.vesam.barexamlibrary.data.repository.quiz_repository.QuizRepository
import org.koin.dsl.module

val repoModule = module {
    single { QuizRepository(get()) }
}