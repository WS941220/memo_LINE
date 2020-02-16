package com.example.memo_line.ui.addeditmemo

import com.example.memo_line.di.Scoped.FragmentScoped
import com.example.memo_line.ui.main.AddEditMemoContract
import com.example.memo_line.ui.main.AddEditMemoPresenter
import com.example.practice_test.di.Scoped.ActivityScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [AddEditMemoModule.AddEditAbstractModule::class])
class AddEditMemoModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface AddEditAbstractModule {
        @ActivityScoped
        @Binds
        fun addEditMemoPresenter(presenter : AddEditMemoPresenter): AddEditMemoContract.Presenter

        @FragmentScoped
        @ContributesAndroidInjector
        fun addEditMemoFragment(): AddEditMemoFragment
    }
}