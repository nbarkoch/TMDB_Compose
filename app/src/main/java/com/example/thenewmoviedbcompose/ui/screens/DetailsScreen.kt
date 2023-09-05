package com.example.thenewmoviedbcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import coil.compose.rememberAsyncImagePainter
import com.example.thenewmoviedbcompose.R
import com.example.thenewmoviedbcompose.components.IMAGE_BASE_URL
import com.example.thenewmoviedbcompose.model.Movie
import com.example.thenewmoviedbcompose.storage.FavoriteMovieDatabase
import com.example.thenewmoviedbcompose.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    navController: NavController, movie: Movie, viewModel: DetailsViewModel
) {

    LaunchedEffect(movie) {
        viewModel.checkIfIsFavorite(movie)
    }

    val containerColor = if (viewModel.isFavorite.value) Color.White
    else Color(0xFF032541)

    val borderColor = if (viewModel.isFavorite.value) Color(0xFF032541)
    else Color.White

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0x70032541),
            Color.Transparent,
        ),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberAsyncImagePainter("$IMAGE_BASE_URL${movie.posterPath}"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 40.dp, start = 30.dp, end = 30.dp)
                    .height(300.dp)
                    .width(200.dp)
                    .clip(
                        shape = RoundedCornerShape(10.dp)
                    )
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = movie.originalTitle, style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp)
            )
            Text(
                text = movie.overview,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

        }
        Box(
            Modifier
                .fillMaxWidth()
                .background(
                    brush = gradientBrush,
                )
                .padding(5.dp)
        ) {
            Button(
                modifier = Modifier.padding(10.dp), onClick = {
                    if (viewModel.isFavorite.value) {
                        viewModel.removeFromFavorites(movie)
                    } else {
                        viewModel.addToFavorites(movie)
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = containerColor
                )

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(
                            id = if (viewModel.isFavorite.value) R.drawable.baseline_favorite_24
                            else R.drawable.baseline_favorite_border_24
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        colorFilter = ColorFilter.tint(
                            borderColor
                        )
                    )
                    Text(
                        text = if (viewModel.isFavorite.value)
                            "Your Favorite" else "Add To Favorite",
                        style = TextStyle(
                            color = borderColor, fontWeight = FontWeight.Bold, fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }

    }
}


@Composable
@Preview
fun DetailsScreenPreview() {
    val context = LocalContext.current
    val dbPreview by lazy {
        Room.databaseBuilder(
            context, FavoriteMovieDatabase::class.java, "favorites.db"
        ).build()
    }
    DetailsScreen(
        navController = rememberNavController(), movie = Movie(
            1, true, "", emptyList(), "", "", "", 0.0, "", "", "", false, 0.0, 0.0
        ), viewModel = DetailsViewModel(dbPreview.dao)
    )
}