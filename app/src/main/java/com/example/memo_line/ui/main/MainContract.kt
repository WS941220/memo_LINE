package com.example.memo_line.ui.main

import com.example.memo_line.base.BaseContract


interface MainContract {
    interface View: BaseContract.View {
        fun showAddMemo()
    }
    interface Presenter: BaseContract.Presenter<View> {

        fun result(requestCode: Int, resultCode: Int)

        fun addNewMemo()
    }
}

