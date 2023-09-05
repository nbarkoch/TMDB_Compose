package com.example.thenewmoviedbcompose.api

import com.example.thenewmoviedbcompose.model.MoviesResponse
import com.example.thenewmoviedbcompose.model.NowPlayingMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): Response<NowPlayingMoviesResponse>

}
