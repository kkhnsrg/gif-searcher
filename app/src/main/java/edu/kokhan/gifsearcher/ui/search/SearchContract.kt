package edu.kokhan.gifsearcher.ui.search

import edu.kokhan.gifsearcher.data.GifInfo
import edu.kokhan.gifsearcher.ui.base.BaseContract

interface SearchContract : BaseContract {

    interface View : BaseContract.BaseView {
        fun init()
        fun initTrendGifList(gifList: List<GifInfo>)
        fun fillSearchGifList(gifList: List<GifInfo>)
        fun addItemsToGifList(gifList: List<GifInfo>)
        fun showRetryButton()
        fun hideRetryButton()
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun start()
        fun onLoadTrends(offset: Int = 0)
        fun onLoadSearchResult(query: String, offset: Int = 0)
        fun onLoadMore(query: String?, offset: Int)
    }
}