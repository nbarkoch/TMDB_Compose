package com.example.thenewmoviedbcompose.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MoviesRetrofitInstance {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "d2bc56bb74d10fcca04542127ebda98c"
    private const val ACCESS_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMmJjNTZiYjc0ZDEwZmNjYTA0NTQyMTI3ZWJkYTk4YyIsInN1YiI6IjY0ZTZlN2ZiMWZlYWMxMDEzOGQ5ODA5MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.iRPhUdF3_CxmmRzrQTxmJingBS2BLUf853AS_ABMjWA"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w220_and_h330_face/"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val requestWithHeaders = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader(
                    "Authorization",
                    "Bearer $ACCESS_TOKEN"
                )
                .build()
            chain.proceed(requestWithHeaders)
        }.build()

    val api: MoviesAPI by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MoviesAPI::class.java)
    }

}