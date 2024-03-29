package com.example.memo_line.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module


@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Application?): Context
}