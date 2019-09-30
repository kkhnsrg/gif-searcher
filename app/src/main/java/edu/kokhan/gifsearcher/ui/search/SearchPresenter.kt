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

private const val ERROR_MESSAGE = "Check your internet connection!"
private const val EMPTY_RESULT_MESSAGE = "No gifs for your query :("

class SearchPresenter : CentralPresenter<SearchContract.View>(), SearchContract.Presenter {

    override fun start() {
        view?.init()
    }

    override fun onLoadMore(query: String?, offset: Int) {
        if (query == null) onLoadTrends(offset) else onLoadSearchResult(query, offset)
    }

    override fun onLoadTrends(offset: Int) {
        GiphyService
            .getTrends(offset)
            .flatMap { Observable.fromIterable(it.data) }
            .map {
                GifInfo(
                    it.images.fixed_width.url,
                    it.images.fixed_width.height,
                    it.images.fixed_width.width
                )
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<List<GifInfo>> {

                override fun onSuccess(data: List<GifInfo>) {
                    if (offset == 0) {
                        view?.hideRetryButton()
                        view?.initTrendGifList(data)
                    } else {
                        view?.addItemsToGifList(data)
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
            .map {
                GifInfo(
                    it.images.fixed_width.url,
                    it.images.fixed_width.height,
                    it.images.fixed_width.width
                )
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<List<GifInfo>> {

                override fun onSuccess(data: List<GifInfo>) {
                    if (data.isEmpty()) {
                        view?.showMessage(EMPTY_RESULT_MESSAGE)
                    } else {
                        if (offset == 0) view?.fillSearchGifList(data)
                        else view?.addItemsToGifList(data)
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