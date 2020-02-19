package com.example.memo_line.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.example.memo_line.base.BasePresenter
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.data.source.MemosRepository
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.memo_line.ui.addeditmemo.AddEditMemoFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AddEditMemoPresenter @Inject  constructor(
    private val memoId: String?,
    private val disposables: CompositeDisposable,
    private val memosRepository: MemosRepository
) : BasePresenter<AddEditMemoContract.View?>(), AddEditMemoContract.Presenter, MemosDataSource.GetMemoCallback {

    override fun subscribe() {
        if (memoId != null) {
            showMemo()
        }
    }

    override fun unsubscribe() {
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: AddEditMemoContract.View) {
        this.view = view
    }

    override fun showMemo() {
        if (memoId == null) {
            throw RuntimeException("memo is new.")
        }
        memosRepository.getMemo(memoId, this)
    }

    override fun onMemoLoaded(memo: Memo) {
        view?.setTitle(memo.title)
        view?.setContent(memo.content)
        view?.setImages(memo.image)
        view?.onShow()
    }


    override fun showMessage(msg: String) {
        view?.showMessage(msg)
    }

    override fun result(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                AddEditMemoFragment.PICK_GALLERY_ID -> {
                    view?.showSuccessGallery(data)
                }
                AddEditMemoFragment.PICK_CAMERA_ID -> {
                    view?.showSuccessCamera()
                }
            }
        }
    }

    /**
     * 메모 저장
     */
    override fun saveMemo(title: String, content: String, image: List<String>) {
        if (memoId == null) {
            createMemo(title, content, image)
        }
//        else {
//            updateTask(title, description)
//        }
    }

    override fun callGallery() {
        view?.showGallery()
    }

    override fun callCamera() {
        view?.showCamera()
    }

    private fun createMemo(title: String, content: String, image: List<String>) {
        val newMemo = Memo(title, content, image)
        if (newMemo.isEmpty) {
//            view.showEmptyTaskError()
        } else {
            memosRepository.insertMemo(newMemo)
            view?.showMemosList()
        }
    }

}
