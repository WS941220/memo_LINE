package com.example.memo_line.di.module

import android.app.Application
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.util.AppExecutors
import com.example.android.architecture.blueprints.todoapp.util.DiskIOThreadExecutor
import com.example.memo_line.data.source.local.MemoDao
import com.example.memo_line.data.source.local.MemoDatabase
import com.example.memo_line.di.Scoped.AppScoped
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
class MemosRepositoryMoudle {

    private val THREAD_COUNT = 3

    @AppScoped
    @Provides
    fun provideMemoDatabase(context: Application): MemoDatabase {
        return Room.databaseBuilder(context.applicationContext,
            MemoDatabase::class.java,
            "Memo.db")
            .build()
    }

    @AppScoped
    @Provides
    fun provideMemoDao(meodDatabase: MemoDatabase): MemoDao {
        return meodDatabase.memoDao()
    }

    @AppScoped
    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors(
            DiskIOThreadExecutor(),
            Executors.newFixedThreadPool(THREAD_COUNT),
            AppExecutors.MainThreadExecutor()
        )
    }

//    @AppScoped
//    @Provides
//    fun memoRepository(memoDao: MemoDao): MemosDataSource {
//        return MemosRepository(memoDao)
//    }
}