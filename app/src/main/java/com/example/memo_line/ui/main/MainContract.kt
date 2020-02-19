package com.example.memo_line.ui.main

import com.example.memo_line.base.BaseContract
import com.example.memo_line.base.BasePresenter
import com.example.memo_line.base.BaseView
import com.example.memo_line.data.Memo


interface MainContract {
    interface View: BaseContract.View {
        var isDelete: Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showMemos(memos: List<Memo> )

        fun showAddMemo()

        fun showOpenMemo(memoId: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadMemos(forceUpdate: Boolean)

        fun result(requestCode: Int, resultCode: Int)

        fun addNewMemo()

        fun deleteMemo()

        fun openMemo(clickMemo: Memo)
    }
}

