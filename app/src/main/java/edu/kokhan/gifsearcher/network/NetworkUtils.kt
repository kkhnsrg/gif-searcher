package edu.kokhan.gifsearcher.network

object NetworkUtils {

    private var api: GiphyApi? = null

    val giphyApiInstance: GiphyApi
        get() {
            if (api == null) {
                api = RetrofitAdapter.instance.create(GiphyApi::class.java)
            }
            return api!!
        }

}
