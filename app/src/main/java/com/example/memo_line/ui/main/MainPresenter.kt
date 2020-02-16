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
    }


    override fun loadMemos() {
//        if (showLoadingUI) {
////            view.setLoadingIndicator(true)
//        }
//        if (forceUpdate) {
//            respository.refreshTasks()
//        }
        memosRepository.getMemos(object : MemosDataSource.LoadMemosCallback {
            override fun onMemoLoaded(memos: List<Memo>) {
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
            // Show the list of tasks
            view?.showMemos(memos)
        }
    }

    private fun processEmptyMemos() {
//        view.showNoMemos()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        // If a task was successfully added, show snackbar
        if (AddEditMemoActivity.REQUEST_ADD_MEMO ==
            requestCode && Activity.RESULT_OK == resultCode
        ) {
//            view.showSuccessfullySavedMessage()
        }
    }


    override fun addNewMemo() {
        view?.showAddMemo()
    }

}
