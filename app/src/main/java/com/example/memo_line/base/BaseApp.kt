package com.example.memo_line.base

import android.app.Application
import com.example.memo_line.BuildConfig
import com.example.memo_line.di.AppComponent
import javax.inject.Inject


class BaseApp: Application() {

    @Inject
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        setup()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    fun setup()  {
//        component =  DaggerApplicationComponent.builder()
//            .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): AppComponent {
        return component
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}