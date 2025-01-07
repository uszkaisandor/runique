package com.uszkaisandor.auth.data.di

import com.uszkaisandor.auth.data.AuthRepositoryImpl
import com.uszkaisandor.auth.data.EmailPatternValidator
import com.uszkaisandor.auth.domain.AuthRepository
import com.uszkaisandor.auth.domain.PatternValidator
import com.uszkaisandor.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator()
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}