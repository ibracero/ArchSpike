package com.ts.archspike

import android.support.v7.app.AppCompatActivity
import com.ts.archspike.common.di.baseActivityModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    private val appKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(appKodein)
        import(baseActivityModule(this@BaseActivity), allowOverride = true)
        import(activityModule())
    }

    open fun activityModule() = Kodein.Module("activity") {}
}