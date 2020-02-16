package com.example.memo_line.base

import androidx.annotation.VisibleForTesting
import com.example.memo_line.data.source.MemosRepository
import com.example.memo_line.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class BaseApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}
