package com.example.thenewmoviedbcompose.storage

import com.example.thenewmoviedbcompose.model.Movie

sealed interface StorageEvent {
    data class addFavorite(val movie: Movie): StorageEvent
    data class removeFavorite(val movie: Movie): StorageEvent
    data class getFavorites(val pageSize: Int, val offset: Int): StorageEvent
}