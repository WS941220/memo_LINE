package com.example.memo_line.ui.main

import com.example.memo_line.base.BaseContract
import com.example.memo_line.base.BaseView
import com.example.memo_line.data.Memo


interface MainContract {
    interface View: BaseContract.View {
        fun showMemos(memos: List<Memo> )

        fun showAddMemo()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadMemos()
        fun result(requestCode: Int, resultCode: Int)
        fun addNewMemo()
    }
}

