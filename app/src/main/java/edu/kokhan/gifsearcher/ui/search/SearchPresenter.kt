package edu.kokhan.gifsearcher.ui.search

import android.util.Log
import edu.kokhan.gifsearcher.data.Data
import edu.kokhan.gifsearcher.network.GiphyService
import edu.kokhan.gifsearcher.ui.base.CentralPresenter
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter : CentralPresenter<SearchContract.View>(), SearchContract.Presenter {

    private val ERROR_MESSAGE = "Check your internet connection!"
    private val EMPTY_RESULT_MESSAGE = "No gifs for your query :("

    override fun start() {
        view?.init()
    }

    override fun onLoadTrends() {
        GiphyService
            .getTrends()
            .flatMap { Observable.fromIterable(it.data) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<List<Data>> {

                override fun onSuccess(data: List<Data>) {
                    view?.hideRetryButton()
                    view?.fillTrendGifList(data.map { it.images.fixed_width.url })
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    view?.showMessage(ERROR_MESSAGE)
                    view?.showRetryButton()
                    Log.e(SearchPresenter::class.java.name, e.message ?: "Unknown error")
                }
            })
    }

    override fun onLoadSearchResult(query: String) {
        GiphyService
            .getSearchResult(query)
            .flatMap { Observable.fromIterable(it.data) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<List<Data>> {

                override fun onSuccess(data: List<Data>) {
                    val urls = data.map { it.images.fixed_width.url }
                    if (urls.isEmpty()) {
                        view?.showMessage(EMPTY_RESULT_MESSAGE)
                    } else {
                        view?.fillSearchGifList(urls)
                    }
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    view?.showMessage(ERROR_MESSAGE)
                    Log.e(SearchPresenter::class.java.name, e.message ?: "Unknown error")
                }
            })
    }
}