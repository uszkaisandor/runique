package com.uszkaisandor.analytics.data.di

import com.uszkaisandor.analytics.data.RoomAnalyticsRepository
import com.uszkaisandor.analytics.domain.AnalyticsRepository
import com.uszkaisandor.core.database.RunDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}