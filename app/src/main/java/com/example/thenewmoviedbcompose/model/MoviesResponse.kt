package com.example.thenewmoviedbcompose.model

import com.example.thenewmoviedbcompose.model.Movie
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)