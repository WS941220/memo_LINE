package com.example.practice_test.di.module

import android.app.Application
import com.example.memo_line.base.BaseApp
import com.example.practice_test.di.Scoped.ActivityScoped
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp)  {

    @Provides
    @Singleton
    @ActivityScoped
    fun provideApplication(): Application {
        return baseApp
    }
}