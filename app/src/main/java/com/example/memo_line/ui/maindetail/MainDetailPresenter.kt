package com.example.memo_line.ui.maindetail

import com.example.memo_line.base.BasePresenter
import com.example.memo_line.data.source.MemosRepository
import io.reactivex.disposables.CompositeDisposable

class MainDetailPresenter(
    val disposables: CompositeDisposable,
    val memosRepository: MemosRepository
) : BasePresenter<MainDetailContract.View?>(), MainDetailContract.Presenter  {

    override fun subscribe() {

    }
    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }
    override fun attach(view: MainDetailContract.View) {
        this.view = view
    }
}