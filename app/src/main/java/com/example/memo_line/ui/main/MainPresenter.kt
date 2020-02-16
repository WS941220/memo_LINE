package com.example.memo_line.ui.main
import android.app.Activity
import android.widget.Toast
import androidx.annotation.Nullable
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource
import com.example.memo_line.data.source.MemosRepository
import com.example.memo_line.data.source.local.MemoDao
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import com.example.practice_test.di.Scoped.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

@ActivityScoped
class MainPresenter : MainContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: MainContract.View

    private val memoDataSource: MemosDataSource? = null


    private var firstLoad = true

    override fun subscribe() {
    }

    override fun unsubscribe() {
        subscriptions.clear()
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
          var a = memoDataSource?.getMemos()
//        processMemos()
    }

    private fun processMemos(memos: ArrayList<Memo>) {
        if (memos.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyMemos()
        } else {
            // Show the list of tasks
            view.showMemos(memos)
        }
    }

    private fun processEmptyMemos() {
//        view.showNoMemos()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        // If a task was successfully added, show snackbar
        if (AddEditMemoActivity.REQUEST_ADD_MEMO ==
            requestCode && Activity.RESULT_OK == resultCode) {
//            view.showSuccessfullySavedMessage()
        }
    }


    override fun addNewMemo() {
        view.showAddMemo()
    }

}
