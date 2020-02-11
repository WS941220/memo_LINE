package com.example.memo_line.di

import com.example.memo_line.base.BaseApp
import com.example.practice_test.di.module.ApplicationModule
import dagger.Component


@Component(modules = arrayOf(ApplicationModule::class))
interface AppComponent {
    fun inject(application: BaseApp)
}