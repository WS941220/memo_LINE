package com.example.memo_line.ui.main
import io.reactivex.disposables.CompositeDisposable

class AddEditMemoPresenter : AddEditMemoContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: AddEditMemoContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: AddEditMemoContract.View) {
        this.view = view
    }

}
