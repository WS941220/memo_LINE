package com.example.practice_test.di.module

import android.app.Application
import com.example.memo_line.base.BaseApp
import com.example.memo_line.di.Scoped.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule (private val baseApp: BaseApp)  {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }

//    @Provides
//    @Singleton
//    internal fun provideMemoDatabase(): MemoDatabase =
//        Room.databaseBuilder(baseApp, MemoDatabase::class.java, "Memo.db").build()
//
//
//    @Provides
//    @Singleton
//    internal fun provideMemoRepository(memoDatabase: MemoDatabase): MemosDataSource = MemosRepository(memoDatabase.memoDao())
}