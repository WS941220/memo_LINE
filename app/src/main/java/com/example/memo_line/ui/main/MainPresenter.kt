package com.example.memo_line.ui.main
import android.app.Activity
import com.example.memo_line.ui.addeditmemo.AddEditMemoActivity
import io.reactivex.disposables.CompositeDisposable

class MainPresenter : MainContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: MainContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
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
