package edu.kokhan.gifsearcher.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAdapter {

    private var retrofit: Retrofit? = null
    private var gson: Gson? = null
    private const val BASE_URL = "http://api.giphy.com/v1/gifs/"

    val instance: Retrofit
        @Synchronized get() {

            if (retrofit == null) {
                if (gson == null) {
                    gson = GsonBuilder().setLenient().create()
                }

                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor { chain ->
                    val original = chain.request()

                    val request = original.newBuilder().build()

                    chain.proceed(request)
                }

                val client = httpClient.build()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson!!))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()

            }

            return retrofit!!
        }

}
