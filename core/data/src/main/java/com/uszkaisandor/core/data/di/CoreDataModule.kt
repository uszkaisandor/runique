package com.uszkaisandor.core.data.di

import com.uszkaisandor.core.data.auth.EncryptedSessionStorage
import com.uszkaisandor.core.data.networking.HttpClientFactory
import com.uszkaisandor.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}