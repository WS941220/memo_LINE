package com.example.practice_test.di.module

import com.example.practice_test.di.Scoped.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [NewsModule::class])
    abstract fun newsActivity(): NewsActivity?
}