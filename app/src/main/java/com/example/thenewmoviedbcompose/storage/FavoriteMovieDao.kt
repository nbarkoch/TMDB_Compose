package com.example.thenewmoviedbcompose.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.thenewmoviedbcompose.model.Movie

@Dao
interface FavoriteMovieDao {

    @Upsert
    suspend fun upsertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movie LIMIT :pageSize OFFSET :offset")
    suspend fun getMovies(pageSize: Int, offset: Int): List<Movie>

    @Query("SELECT COUNT(*) FROM movie WHERE movie.id = :movieId")
    suspend fun doesMovieExist(movieId: Int): Boolean

}