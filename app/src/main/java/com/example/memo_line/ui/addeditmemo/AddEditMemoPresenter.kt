package com.example.memo_line.ui.main
import android.app.Activity
import android.content.Intent
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.memo_line.ui.addeditmemo.AddEditMemoFragment
import io.reactivex.disposables.CompositeDisposable

class AddEditMemoPresenter : AddEditMemoContract.Presenter {

    private val subscriptions = CompositeDisposable()

    private val memoId: String? = null
    private val respository: MemosDataSource? = null
    private lateinit var view: AddEditMemoContract.View

    override fun subscribe() {

    }
    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: AddEditMemoContract.View) {
        this.view = view
    }
    override fun showMessage(msg: String) {
        view.showMessage(msg)
    }

    override fun result(requestCode: Int, resultCode: Int, data: Intent?) {
        if (AddEditMemoFragment.PICK_GALLERY_ID ==
            requestCode && Activity.RESULT_OK == resultCode) {
            view.showSuccessGallery(data)
        }
    }

    override fun saveMemo(title: String, content: String) {
        if (memoId == null) {
            createMemo(title, content)
        }
//        else {
//            updateTask(title, description)
//        }
    }

    override fun callGallery() {
        view.showGallery()
    }

    private fun createMemo(title: String, content: String) {
        val newMemo = Memo(title, content)
        if (newMemo.isEmpty) {
//            view.showEmptyTaskError()
        } else {
            respository?.saveMemo(newMemo)
            view.showMemosList()
        }
    }

}
