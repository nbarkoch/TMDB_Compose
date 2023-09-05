package com.example.thenewmoviedbcompose.model

import android.media.browse.MediaBrowser.MediaItem
import com.google.gson.annotations.SerializedName


data class GenreItem(
    val id: Int,
    val name: String,
)

data class MovieCollection (
    val id: Int,
    val name: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    )

data class MovieDetailsResponse(
    val id: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("belongs_to_collection") val belongsToCollection: MovieCollection,
    val budget: Int,
    val genres: List<GenreItem>,
    val homepage: String,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    )
