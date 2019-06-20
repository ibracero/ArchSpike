package com.ts.archspike

import android.app.Application
import com.ts.archspike.common.di.repositoryModule
import com.ts.archspike.common.di.viewModelModule
import com.ts.archspike.common.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(listOf(retrofitModule, repositoryModule, viewModelModule))
        }
    }
}
