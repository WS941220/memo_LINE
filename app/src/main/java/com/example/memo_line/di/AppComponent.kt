package com.example.memo_line.di

import android.app.Application
import com.example.memo_line.base.BaseApp
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.data.source.local.MemoDao
import com.example.memo_line.data.source.local.MemoDatabase
import com.example.memo_line.di.Scoped.AppScoped
import com.example.memo_line.di.module.RepositoryMoudle
import com.example.memo_line.ui.main.MainActivity
import com.example.practice_test.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@AppScoped
@Component(modules = [(ApplicationModule::class), (RepositoryMoudle::class)])
interface AppComponent {

    fun inject(baseApp: BaseApp)

    fun memoDao(): MemoDao

    fun memoDatabase(): MemoDatabase

    fun memoDataSource(): MemosDataSource


}