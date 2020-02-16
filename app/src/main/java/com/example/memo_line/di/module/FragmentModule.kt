package com.example.memo_line.di.module

import com.example.memo_line.ui.addeditmemo.AddEditMemoFragment
import com.example.memo_line.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeAddEditMemoFragment(): AddEditMemoFragment
}