package com.example.memo_line.di.moudle

import com.example.memo_line.di.Scoped.ActivityScoped
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.memo_line.ui.addeditmemo.AddEditMemoModule
import com.example.memo_line.ui.main.MainActivity
import com.example.memo_line.ui.main.MainModule

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AddEditMemoModule::class])
    abstract fun addEditMemoActivity(): AddEditMemoActivity

}