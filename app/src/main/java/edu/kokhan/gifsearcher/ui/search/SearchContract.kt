package edu.kokhan.gifsearcher.ui.search

import edu.kokhan.gifsearcher.data.GifInfo
import edu.kokhan.gifsearcher.ui.base.BaseContract

interface SearchContract : BaseContract {

    interface View : BaseContract.BaseView {
        fun init()
        fun fillTrendGifList(gifList: List<GifInfo>)
        fun fillSearchGifList(gifList: List<GifInfo>)
        fun showRetryButton()
        fun hideRetryButton()
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun start()
        fun onLoadTrends()
        fun onLoadSearchResult(query: String)
    }
}