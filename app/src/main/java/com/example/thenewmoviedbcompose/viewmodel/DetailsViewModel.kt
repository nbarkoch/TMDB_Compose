package com.example.thenewmoviedbcompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenewmoviedbcompose.model.Movie
import com.example.thenewmoviedbcompose.storage.FavoriteMovieDao
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val favoriteMovieDao: FavoriteMovieDao
): ViewModel() {

    private val _isFavorite = mutableStateOf(false)
    val isFavorite: MutableState<Boolean> = _isFavorite

    fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            favoriteMovieDao.upsertMovie(movie)
            checkIfIsFavorite(movie)
        }
    }

    fun removeFromFavorites(movie: Movie) {
        viewModelScope.launch {
            favoriteMovieDao.deleteMovie(movie)
            checkIfIsFavorite(movie)
        }
    }

    fun checkIfIsFavorite(movie: Movie) {
        viewModelScope.launch {
            _isFavorite.value = favoriteMovieDao.doesMovieExist(movie.id)
        }
    }

}