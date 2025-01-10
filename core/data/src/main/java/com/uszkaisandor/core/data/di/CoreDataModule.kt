package com.uszkaisandor.core.data.di

import com.uszkaisandor.core.data.auth.EncryptedSessionStorage
import com.uszkaisandor.core.data.networking.HttpClientFactory
import com.uszkaisandor.core.data.run.OfflineFirstRunRepository
import com.uszkaisandor.core.domain.SessionStorage
import com.uszkaisandor.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}