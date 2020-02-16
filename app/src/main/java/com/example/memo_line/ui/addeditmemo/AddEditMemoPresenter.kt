package com.example.memo_line.ui.main
import android.app.Activity
import android.content.Intent
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.data.source.MemosRepository
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.memo_line.ui.addeditmemo.AddEditMemoFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AddEditMemoPresenter : AddEditMemoContract.Presenter {

    private val subscriptions = CompositeDisposable()

    private val memoId: String? = null

    private val memosDatSource: MemosDataSource? = null

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
        if(Activity.RESULT_OK == resultCode) {
            when(requestCode) {
                AddEditMemoFragment.PICK_GALLERY_ID ->{
                    view.showSuccessGallery(data)
                }
                AddEditMemoFragment.PICK_CAMERA_ID -> {
                    view.showSuccessCamera()
                }
            }
        }
    }

    /**
     * 메모 저장
     */
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

    override fun callCamera() {
        view.showCamera()
    }

    private fun createMemo(title: String, content: String) {
        val newMemo = Memo(title, content)
        if (newMemo.isEmpty) {
//            view.showEmptyTaskError()
        } else {
            memosDatSource?.insertMemo(newMemo)
            view.showMemosList()
        }
    }

}
