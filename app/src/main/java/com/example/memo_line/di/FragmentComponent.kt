package com.example.memo_line.di

import com.example.memo_line.di.module.FragmentModule
import com.example.memo_line.ui.addeditmemo.AddEditMemoFragment
import com.example.memo_line.ui.main.MainFragment
import com.example.memo_line.ui.maindetail.MainDetailFragment
import dagger.Component

@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(mainFragment: MainFragment)

    fun inject(addEditMemoFragment: AddEditMemoFragment)

    fun inject(mainDetailFragment: MainDetailFragment)

}