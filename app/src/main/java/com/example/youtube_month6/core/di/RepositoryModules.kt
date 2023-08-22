package com.example.youtube_month6.core.di

import com.example.youtube_month6.repository.Repository
import org.koin.dsl.module

val repositoryModules = module {
    single { Repository(get()) }
}