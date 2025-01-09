package com.uszkaisandor.run.data.di

import com.uszkaisandor.run.data.CreateRunWorker
import com.uszkaisandor.run.data.DeleteRunWorker
import com.uszkaisandor.run.data.FetchRunsWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)
}