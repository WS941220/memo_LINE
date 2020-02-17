package com.example.memo_line.ui.main

import android.content.Intent
import android.net.Uri
import com.example.memo_line.base.BaseContract
import com.example.memo_line.base.BasePresenter
import com.example.memo_line.base.BaseView


interface AddEditMemoContract {
    interface View: BaseContract.View {
        fun showMessage(msg: String)

        fun showMemosList()

        fun showFilteringPopUpMenu()

        fun showGallery()

        fun showCamera()

        fun showSuccessGallery(data: Intent?)

        fun showSuccessCamera()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun showMessage(msg: String)

        fun result(requestCode: Int, resultCode: Int, data: Intent?)

        fun saveMemo(title: String, content: String, image: List<String>)

        fun callGallery()

        fun callCamera()
    }
}


