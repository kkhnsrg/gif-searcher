package edu.kokhan.gifsearcher.network


import edu.kokhan.gifsearcher.data.DataObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GiphyApi {

    @Headers(
        value = ["api_key: fws8qXsipc79cqR5SkBHdyvZZTsYGgBj",
            "raiting: G"]
    )
    @GET("trending")
    fun getTrends(@Query("limit") limit: Int, @Query("offset") offset: Int): Observable<DataObject>

    @Headers(
        value = ["api_key: fws8qXsipc79cqR5SkBHdyvZZTsYGgBj",
            "raiting: G",
            "lang: en"]
    )
    @GET("search")
    fun getSearchResult(@Query("q") query: String, @Query("limit") limit: Int,
                        @Query("offset") offset: Int): Observable<DataObject>

}
