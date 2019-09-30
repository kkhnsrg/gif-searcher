package edu.kokhan.gifsearcher.ui.search

import android.util.Log
import edu.kokhan.gifsearcher.data.Data
import edu.kokhan.gifsearcher.data.GifInfo
import edu.kokhan.gifsearcher.network.GiphyService
import edu.kokhan.gifsearcher.ui.base.CentralPresenter
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter : CentralPresenter<SearchContract.View>(), SearchContract.Presenter {
    override fun onLoadMore(query: String?, offset: Int) {
        if (query == null) onLoadTrends(offset) else onLoadSearchResult(query, offset)
    }

    private val ERROR_MESSAGE = "Check your internet connection!"
    private val EMPTY_RESULT_MESSAGE = "No gifs for your query :("

    override fun start() {
        view?.init()
    }

    override fun onLoadTrends(offset: Int) {
        GiphyService
            .getTrends(offset)
            .flatMap { Observable.fromIterable(it.data) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<List<Data>> {

                override fun onSuccess(data: List<Data>) {
                    val gifs = data.map {
                        GifInfo(
                            it.images.fixed_width.url,
                            it.images.fixed_width.height,
                            it.images.fixed_width.width
                        )
                    }
                    if (offset == 0) {
                        view?.hideRetryButton()
                        view?.initTrendGifList(gifs)
                    } else {
                        view?.addItemsToGifList(gifs)
                    }
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    view?.showMessage(ERROR_MESSAGE)
                    if (offset == 0) view?.showRetryButton()
                    Log.e(SearchPresenter::class.java.name, e.message ?: "Unknown error")
                }
            })
    }

    override fun onLoadSearchResult(query: String, offset: Int) {
        GiphyService
            .getSearchResult(query, offset)
            .flatMap { Observable.fromIterable(it.data) }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<List<Data>> {

                override fun onSuccess(data: List<Data>) {
                    val urls = data.map {
                        GifInfo(
                            it.images.fixed_width.url,
                            it.images.fixed_width.height,
                            it.images.fixed_width.width
                        )
                    }
                    if (urls.isEmpty()) {
                        view?.showMessage(EMPTY_RESULT_MESSAGE)
                    } else {
                        if (offset == 0) view?.fillSearchGifList(urls) else view?.addItemsToGifList(
                            urls
                        )
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