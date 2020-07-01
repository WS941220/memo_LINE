package com.example.memo_line.di

import android.app.Application
import com.example.memo_line.base.BaseApp
import com.example.memo_line.data.source.MemosRepository
import com.example.memo_line.di.Scoped.AppScoped
import com.example.memo_line.di.module.ApplicationModule
import com.example.memo_line.di.module.MemosRepositoryMoudle
import com.example.memo_line.di.moudle.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(modules = [(MemosRepositoryMoudle::class), (ApplicationModule::class), (ActivityModule::class), (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<BaseApp> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}