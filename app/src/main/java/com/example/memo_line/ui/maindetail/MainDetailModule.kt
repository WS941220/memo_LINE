package com.example.memo_line.ui.maindetail

import com.example.memo_line.di.Scoped.FragmentScoped
import com.example.practice_test.di.Scoped.ActivityScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [MainDetailModule.MainAbstractModule::class])
class MainDetailModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface MainAbstractModule {
        @ActivityScoped
        @Binds
        fun mainDetailPresenter(presenter : MainDetailPresenter): MainDetailContract.Presenter

        @FragmentScoped
        @ContributesAndroidInjector
        fun mainDetailFragment(): MainDetailFragment
    }
}