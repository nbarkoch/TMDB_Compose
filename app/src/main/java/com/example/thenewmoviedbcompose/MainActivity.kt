package com.example.thenewmoviedbcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thenewmoviedbcompose.model.Movie
import com.example.thenewmoviedbcompose.ui.screens.DetailsScreen
import com.example.thenewmoviedbcompose.ui.screens.HomeScreen
import com.example.thenewmoviedbcompose.ui.theme.TheNewMovieDBcomposeTheme
import com.example.thenewmoviedbcompose.viewmodel.DetailsViewModel
import com.example.thenewmoviedbcompose.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var navController: NavHostController
        val homeViewModel = HomeViewModel()
        val detailsViewModel = DetailsViewModel()
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheNewMovieDBcomposeTheme {
        Greeting("Android")
    }
}