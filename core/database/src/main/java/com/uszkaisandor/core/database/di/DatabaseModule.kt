package com.uszkaisandor.core.database.di

import androidx.room.Room
import com.uszkaisandor.core.database.RoomLocalRunDataSource
import com.uszkaisandor.core.database.RunDatabase
import com.uszkaisandor.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RunDatabase::class.java,
            "run_db"
        ).build()
    }

    single { get<RunDatabase>().runDao }
    single { get<RunDatabase>().runPendingSyncDao }
    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}