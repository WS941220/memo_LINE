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

    /**
     * 메모  가져옴
     * */
    override fun showMemo() {
        if (memoId == null) {
            throw RuntimeException("memo is new.")
        }
        memosRepository.getMemo(memoId, this)
    }

    /**
     * 저장된 메모 로드
     */
    override fun onMemoLoaded(memo: Memo) {
        view?.setTitle(memo.title)
        view?.setContent(memo.content)
        view?.setImages(memo.image)
        view?.onShow()
    }


    /**
     * 스낵바 메세지
     */
    override fun showMessage(msg: String) {
        view?.showMessage(msg)
    }

    /**
     * 갤러리 or 카메라 눌렀을시 결과코드
     */
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
     * 메모 저장 & 업데이트
     */
    override fun saveMemo(title: String, content: String, image: List<String>) {
        if (memoId == null) {
            createMemo(title, content, image)
        }
        else {
            updateMemo(title, content, image)
        }
    }

    /**
     * 메모 삭제
     */
    override fun deleteMemo(memoId: String) {
        memosRepository.deleteMemo(memoId)
        view?.showMemosDeleted()
    }

    /**
     * 갤러리 Call
     */
    override fun callGallery() {
        view?.showGallery()
    }

    /**
     * 카메라 Call
     */
    override fun callCamera() {
        view?.showCamera()
    }

    /**
     * URL링크 Call
     */
    override fun callUrl() {
        view?.showUrl()
    }

    /**
     * 메모 저장
     */
    private fun createMemo(title: String, content: String, image: List<String>) {
        val newMemo = Memo(title, content, image)
        if (newMemo.isEmpty) {
            view?.showEmptyMemo()
        } else {
            memosRepository.insertMemo(newMemo)
            view?.showMemosList()
        }
    }

    /**
     * 메모 업데이트
     */
    private fun updateMemo(title: String, content: String, image: List<String>) {
        if (memoId == null) {
            throw RuntimeException("memo is new.")
        }
        memosRepository.insertMemo(Memo(title, content, image, memoId))
        view?.showMemosList()
    }

}
