package com.example.memo_line.ui.main

import android.content.Intent
import com.example.memo_line.base.BaseContract


interface AddEditMemoContract {
    interface View: BaseContract.View {
        fun showMessage(msg: String)

        fun showMemosList()

        fun showFilteringPopUpMenu()

        fun showGallery()

        fun showSuccessGallery(data: Intent?)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun showMessage(msg: String)

        fun result(requestCode: Int, resultCode: Int, data: Intent?)

        fun saveMemo(title: String, content: String)

        fun callGallery()
    }
}

