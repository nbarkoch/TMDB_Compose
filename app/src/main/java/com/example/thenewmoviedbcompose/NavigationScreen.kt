package com.example.thenewmoviedbcompose

const val MOVIE_ENTITY = "movie"

sealed class NavigationScreen(val route: String) {
    object Home: NavigationScreen("home")
    object Details: NavigationScreen("details")
}