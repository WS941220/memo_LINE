package com.example.memo_line.base


abstract class BasePresenter<V> {
    protected var view: V? = null

    fun subscribe(view: V) {
        this.view = view
    }

    fun unsubscribe() {
        view = null
    }
}