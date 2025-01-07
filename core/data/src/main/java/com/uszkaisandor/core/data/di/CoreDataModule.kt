package com.uszkaisandor.core.data.di

import com.uszkaisandor.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}