package edu.kokhan.gifsearcher.network

import edu.kokhan.gifsearcher.data.DataObject
import io.reactivex.Observable

object GiphyService {

    private const val STATIC_LIMIT = 50

    fun getTrends(offset: Int): Observable<DataObject> {
        return NetworkUtils.giphyApiInstance
            .getTrends(STATIC_LIMIT, offset)
    }

    fun getSearchResult(query: String, offset: Int): Observable<DataObject> {
        return NetworkUtils.giphyApiInstance
            .getSearchResult(query, STATIC_LIMIT, offset)
    }
}
