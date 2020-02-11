package com.example.practice_test.di.module

import android.app.Activity
import com.example.memo_line.ui.main.AddEditMemoContract
import com.example.memo_line.ui.main.AddEditMemoPresenter
import com.example.memo_line.ui.main.MainContract
import com.example.memo_line.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {
    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePresenter(): MainContract.Presenter  {
        return MainPresenter()
    }



}