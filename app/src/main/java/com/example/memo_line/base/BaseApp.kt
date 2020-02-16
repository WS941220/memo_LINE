package com.example.memo_line.base

import android.app.Application
import com.example.memo_line.BuildConfig
import com.example.memo_line.di.AppComponent
import com.example.memo_line.di.DaggerAppComponent
import com.example.memo_line.di.module.RepositoryMoudle
import com.example.practice_test.di.module.ApplicationModule
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
        component = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .repositoryMoudle(RepositoryMoudle(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): AppComponent {
        return component
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}