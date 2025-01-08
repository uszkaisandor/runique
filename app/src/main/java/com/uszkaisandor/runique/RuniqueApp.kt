package com.uszkaisandor.runique

import android.app.Application
import com.uszkaisandor.auth.data.di.authDataModule
import com.uszkaisandor.auth.presentation.di.authViewModelModule
import com.uszkaisandor.core.data.di.coreDataModule
import com.uszkaisandor.run.presentation.di.runViewModelModule
import com.uszkaisandor.runique.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runViewModelModule
            )
        }
    }
}