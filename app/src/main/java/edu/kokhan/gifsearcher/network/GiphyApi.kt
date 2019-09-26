package edu.kokhan.gifsearcher.network


import edu.kokhan.gifsearcher.data.DataObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GiphyApi {

    @Headers(
        value = ["api_key: fws8qXsipc79cqR5SkBHdyvZZTsYGgBj",
            "limit: 25",
            "raiting: G"]
    )
    @GET("trending")
    fun getTrends(): Observable<DataObject>

    @Headers(
        value = ["api_key: fws8qXsipc79cqR5SkBHdyvZZTsYGgBj",
            "limit: 25",
            "offset: 0",
            "raiting: G",
            "lang: en"]
    )
    @GET("search")
    fun getSearchResult(@Query("q") query: String): Observable<DataObject>

}
