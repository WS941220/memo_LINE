package com.example.memo_line.base

abstract class BasePresenter<V> {
    protected var view: V? = null

    open fun subscribe(view: V) {
        this.view = view
    }

    open fun unsubscribe() {
        view = null
    }
}