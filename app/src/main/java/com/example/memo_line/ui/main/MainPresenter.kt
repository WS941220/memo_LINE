package com.example.memo_line.ui.main

import android.app.Activity
import com.example.memo_line.base.BasePresenter
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.data.source.MemosRepository
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.practice_test.di.Scoped.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

@ActivityScoped
class MainPresenter @Inject constructor(
    val disposables: CompositeDisposable,
    val memosRepository: MemosRepository
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

    override fun loadMemos(forceUpdate: Boolean) {
        // Simplification for sample: a network reload will be forced on first load.
        loadMemos(forceUpdate || firstLoad, true)
        firstLoad = false
    }


    private fun loadMemos(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            if (view != null) {
                view?.setLoadingIndicator(true)
            }
        }
        if (forceUpdate) {
            memosRepository.refreshMemos()
        }

        memosRepository.getMemos(object : MemosDataSource.LoadMemosCallback {
            override fun onMemosLoaded(memos: List<Memo>) {
                val memosToShow = ArrayList<Memo>()
                for (memo in memos) {
                    memosToShow.add(memo)
                }

                if (showLoadingUI) {
                    view?.setLoadingIndicator(false)
                }
                processMemos(memosToShow)
            }

            override fun onDataNotAvailable() {
            }
        })

        if (showLoadingUI) {
            view?.setLoadingIndicator(false)
        }
    }

    private fun processMemos(memos: List<Memo>) {
        if (memos.isEmpty()) {
            processEmptyMemos()
        } else {
            // Show the list of tasks
            view?.showMemos(memos)
        }
    }

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

    override fun openMemo(requestMemo: Memo) {
        view?.showOpenMemo(requestMemo.id)
    }

    override fun deleteMemo(memoId: String) {
        memosRepository.deleteMemo(memoId)
    }

    override fun deleteMemos(memoIds: List<String>) {
        memosRepository.deleteMemos(memoIds)
    }

    override fun addNewMemo() {
        view?.showAddMemo()
    }


}
