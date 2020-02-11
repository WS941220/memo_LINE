package com.example.memo_line.di

import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.memo_line.ui.main.MainActivity
import com.example.memo_line.ui.maindetail.MainDetailActivity
import com.example.practice_test.di.module.ActivityModule
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(addEditMemoActivity: AddEditMemoActivity)

    fun inject(mainDetailActivity: MainDetailActivity)

}