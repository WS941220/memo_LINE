package com.example.memo_line.di.module

import com.example.memo_line.ui.main.AddEditMemoContract
import com.example.memo_line.ui.main.AddEditMemoPresenter
import com.example.memo_line.ui.main.MainContract
import com.example.memo_line.ui.main.MainPresenter
import com.example.memo_line.ui.maindetail.MainDetailContract
import com.example.memo_line.ui.maindetail.MainDetailPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideMainPresenter() : MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideAddEditMemoPresenter(): AddEditMemoContract.Presenter {
        return AddEditMemoPresenter()
    }

    @Provides
    fun provideMainDetailPresenter(): MainDetailContract.Presenter {
        return MainDetailPresenter()
    }

}