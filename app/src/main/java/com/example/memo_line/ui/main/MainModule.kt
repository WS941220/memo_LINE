package com.example.memo_line.ui.main

import com.example.memo_line.di.Scoped.FragmentScoped
import com.example.memo_line.di.Scoped.ActivityScoped

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [MainModule.MainAbstractModule::class])
class MainModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface MainAbstractModule {
        @ActivityScoped
        @Binds
        fun mainPresenter(presenter : MainPresenter): MainContract.Presenter

        @FragmentScoped
        @ContributesAndroidInjector
        fun mainFragment(): MainFragment
    }
}