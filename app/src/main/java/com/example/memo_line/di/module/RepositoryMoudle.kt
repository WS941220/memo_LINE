package com.example.memo_line.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.memo_line.base.BaseApp
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.data.source.MemosRepository
import com.example.memo_line.data.source.local.MemoDao
import com.example.memo_line.data.source.local.MemoDatabase
import com.example.memo_line.di.Scoped.AppScoped
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryMoudle (private var baseApp: BaseApp) {


    @AppScoped
    @Provides
    fun provideMemoDatabase(): MemoDatabase {
        return Room.databaseBuilder(baseApp, MemoDatabase::class.java, "Memo.db").allowMainThreadQueries().build()
    }

    @AppScoped
    @Provides
    fun provideMemoDao(meodDatabase: MemoDatabase): MemoDao {
        return meodDatabase.memoDao()
    }

    @AppScoped
    @Provides
    fun memoRepository(memoDao: MemoDao): MemosDataSource {
        return MemosRepository(memoDao)
    }
}