package edu.kokhan.gifsearcher.ui.base

open class CentralPresenter<V : BaseContract.BaseView> : BaseContract.BasePresenter<V> {

    var view: V? = null
        private set

    private val isViewAttached: Boolean
        get() = view != null

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

    internal fun checkViewAttached() {
        if (!isViewAttached) throw RuntimeException()
    }
}
