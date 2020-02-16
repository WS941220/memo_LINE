package com.example.memo_line.di

import com.example.memo_line.di.Scoped.FragmentScoped
import com.example.memo_line.di.module.FragmentModule
import com.example.memo_line.di.module.RepositoryMoudle
import com.example.memo_line.ui.addeditmemo.AddEditMemoFragment
import com.example.memo_line.ui.main.MainFragment
import com.example.memo_line.ui.maindetail.MainDetailFragment
import com.example.practice_test.di.Scoped.ActivityScoped
import com.example.practice_test.di.module.ApplicationModule
import dagger.Component

@ActivityScoped
@Component(modules =  [(FragmentModule::class)])
interface FragmentComponent {
    fun inject(mainFragment: MainFragment)

    fun inject(addEditMemoFragment: AddEditMemoFragment)

    fun inject(mainDetailFragment: MainDetailFragment)

}