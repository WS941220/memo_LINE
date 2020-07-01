package com.example.memo_line.ui.main

import android.app.Activity
import com.example.memo_line.base.BasePresenter
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.data.source.MemosRepository
import com.example.memo_line.di.Scoped.ActivityScoped
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

@ActivityScoped
class MainPresenter @Inject constructor(
    private val disposables: CompositeDisposable,
    private val memosRepository: MemosRepository
) : BasePresenter<MainContract.View?>(), MainContract.Presenter {

    private var firstLoad = true

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
        loadMemos(false)
    }

    /**
     * 스낵바 메세지
     */
    override fun showMessage(msg: String) {
        view?.showMessage(msg)
    }

    override fun loadMemos(forceUpdate: Boolean) {
        loadMemoss(forceUpdate || firstLoad)
        firstLoad = false
    }

    /**
     *메모리스트 로드
     */
    private fun loadMemoss(forceUpdate: Boolean) {
        if (forceUpdate) {
            memosRepository.refreshMemos()
        }

        /**
         * 메모리스트 가져옴
         */
        memosRepository.getMemos(object : MemosDataSource.LoadMemosCallback {
            override fun onMemosLoaded(memos: List<Memo>) {
                val memosToShow = ArrayList<Memo>()
                for (memo in memos) {
                    memosToShow.add(memo)
                }
                processMemos(memosToShow)
            }
            override fun onDataNotAvailable() {

            }
        })

    }


    private fun processMemos(memos: List<Memo>) {
        if (memos.isEmpty()) {
            processEmptyMemos()
        } else {
            view?.showMemos(memos)
        }
    }

    /**
     * 가져온 메모리스트가 없을 시
     */
    private fun processEmptyMemos() {
        view?.showNoMemos()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        // If a task was successfully added, show snackbar
        if (AddEditMemoActivity.REQUEST_ADD_MEMO ==
            requestCode && Activity.RESULT_OK == resultCode
        ) {
            view?.showSuccessfullySavedMessage()
        }
    }

    /**
     * 메모클릭
     */
    override fun openMemo(clickMemo: Memo) {
        view?.showOpenMemo(clickMemo.id)
    }


    /**
     * 메모 삭제
     */
    override fun deleteMemo(memoId: String) {
        memosRepository.deleteMemo(memoId)
    }

    /**
     * 전체 선택
     */
    override fun onCheckAllMemos() {
        memosRepository.checkAllMemo()
    }

    /**
     * 전체 선택 취소
     */
    override fun onCancelAllMemos() {
        memosRepository.cancelAllMemo()
    }

    /**
     * 메모 선택
     */
    override fun checkedMemo(checkedMemo: Memo) {
        memosRepository.checkedMemo(checkedMemo)
    }

    /**
     * 선택 취소
     */
    override fun canceledMemo(canceledMemo: Memo) {
        memosRepository.canceledMemo(canceledMemo)
    }

    /**
     * 선택메모 삭제
     */
    override fun deleteCheckedMemos() {
        memosRepository.deleteCheckedMemos()
        loadMemos(false)
        view?.showDeleteMemos()
    }

    /**
     * 메모추가 눌렀을 시
     */
    override fun addNewMemo() {
        view?.showAddMemo()
    }


}
