package com.ts.archspike

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.ts.archspike.data.di.dataModule

class MainApp : Application(), KodeinAware {

    override val kodein = Kodein {
        import(dataModule)
    }

}
