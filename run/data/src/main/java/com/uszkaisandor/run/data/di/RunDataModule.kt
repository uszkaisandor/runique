package com.uszkaisandor.run.data.di

import com.uszkaisandor.core.domain.run.SyncRunScheduler
import com.uszkaisandor.run.data.CreateRunWorker
import com.uszkaisandor.run.data.DeleteRunWorker
import com.uszkaisandor.run.data.FetchRunsWorker
import com.uszkaisandor.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}