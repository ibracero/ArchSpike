package com.ts.archspike

import android.app.Application
import com.ts.archspike.common.di.appModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class MainApp : Application(), KodeinAware {

    override val kodein = Kodein {
        import(appModule)
    }

}
