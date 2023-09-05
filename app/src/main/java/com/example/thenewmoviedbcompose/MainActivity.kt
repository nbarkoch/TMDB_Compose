package com.example.thenewmoviedbcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.thenewmoviedbcompose.model.Movie
import com.example.thenewmoviedbcompose.storage.FavoriteMovieDatabase
import com.example.thenewmoviedbcompose.ui.screens.DetailsScreen
import com.example.thenewmoviedbcompose.ui.screens.HomeScreen
import com.example.thenewmoviedbcompose.ui.theme.TheNewMovieDBcomposeTheme
import com.example.thenewmoviedbcompose.viewmodel.DetailsViewModel
import com.example.thenewmoviedbcompose.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            FavoriteMovieDatabase::class.java,
            "favorites.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var navController: NavHostController
        val homeViewModel = HomeViewModel(db.dao)
        val detailsViewModel = DetailsViewModel(db.dao)
        super.onCreate(savedInstanceState)
        setContent {
            TheNewMovieDBcomposeTheme {
                navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreen.Home.route
                ) {
                    composable(route = NavigationScreen.Home.route) {
                        HomeScreen(navController, homeViewModel)
                    }
                    composable(route = NavigationScreen.Details.route) {
                        (it.arguments?.getSerializable(MOVIE_ENTITY) as Movie?)?.let { movie ->
                            DetailsScreen(navController = navController, movie = movie, viewModel = detailsViewModel)
                        }
                    }
                }
            }
        }
    }
}