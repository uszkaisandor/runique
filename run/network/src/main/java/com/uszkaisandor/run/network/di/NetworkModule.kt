package com.uszkaisandor.run.network.di

import com.uszkaisandor.core.domain.run.RemoteRunDataSource
import com.uszkaisandor.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}