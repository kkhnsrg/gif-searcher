package edu.kokhan.gifsearcher.ui.base

interface BaseContract {

    interface BasePresenter<V> {
        fun attach(view: V)
        fun detach()
    }

    interface BaseView {
        fun showMessage(message: String)
    }
}
