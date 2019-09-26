package edu.kokhan.gifsearcher.network

import edu.kokhan.gifsearcher.data.DataObject
import io.reactivex.Observable

object GiphyService {

    fun getTrends(): Observable<DataObject> {
        return NetworkUtils.giphyApiInstance
            .getTrends()
    }

    fun getSearchResult(query: String): Observable<DataObject> {
        return NetworkUtils.giphyApiInstance
            .getSearchResult(query)
    }
}
