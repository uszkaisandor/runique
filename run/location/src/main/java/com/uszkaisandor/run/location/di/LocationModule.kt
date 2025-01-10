package com.uszkaisandor.run.location.di

import com.uszkaisandor.run.domain.LocationObserver
import com.uszkaisandor.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}