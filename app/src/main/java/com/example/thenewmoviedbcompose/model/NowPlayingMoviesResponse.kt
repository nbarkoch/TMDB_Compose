package com.example.thenewmoviedbcompose.model

import com.google.gson.annotations.SerializedName

data class Dates(
    val maximum: String,
    val minimum: String,
)

data class NowPlayingMoviesResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
