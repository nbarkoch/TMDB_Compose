package com.example.thenewmoviedbcompose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.example.thenewmoviedbcompose.model.Movie
import com.example.thenewmoviedbcompose.storage.FavoriteMovieDao
import com.example.thenewmoviedbcompose.storage.FavoriteMovieDatabase

val moviePreviewMock =
    Movie(
        id = 615656,
        adult = false,
        backdropPath = "/8pjWz2lt29KyVGoq1mXYu6Br7dE.jpg",
        genreIds = listOf(28, 878, 27),
        originalLanguage = "en",
        originalTitle = "Meg 2: The Trench",
        overview = "An exploratory dive into the deepest depths of the ocean of a daring research team spirals into chaos when a malevolent mining operation threatens their mission and forces them into a high-stakes battle for survival.",
        popularity = 6839.574,
        posterPath = "/FQHtuf2zc8suMFE28RyvFt3FJN.jpg",
        releaseDate = "2023-08-02",
        title = "Meg 2: The Trench",
        video = false,
        voteAverage = 7.1,
        voteCount = 1383.0
    )

@Composable
fun daoMock(): FavoriteMovieDao {
    val context = LocalContext.current
    val dbPreview by lazy {
        Room.databaseBuilder(
            context,
            FavoriteMovieDatabase::class.java,
            "favorites.db"
        ).build()
    }
    return dbPreview.dao
}