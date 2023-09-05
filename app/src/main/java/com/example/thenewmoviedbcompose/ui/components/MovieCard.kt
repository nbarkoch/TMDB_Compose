package com.example.thenewmoviedbcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.thenewmoviedbcompose.api.MoviesRetrofitInstance.IMAGE_BASE_URL
import com.example.thenewmoviedbcompose.model.Movie
import com.example.thenewmoviedbcompose.ui.moviePreviewMock

@Composable
fun MovieCard(movie: Movie, onClick: (movie: Movie) -> Unit) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.White,
        ),
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            Row(modifier = Modifier.height(200.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = movie.title, fontSize = 18.sp, style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = movie.releaseDate,
                            style = TextStyle(
                                color = Color.LightGray,
                            ),
                            fontSize = 12.sp,
                        )
                    }
                    Box(
                        Modifier
                            .padding(10.dp)
                            .clickable {
                                onClick(movie)
                            }) {
                        Text(
                            text = movie.overview, style = TextStyle(
                                fontSize = 14.sp,
                            ), modifier = Modifier.fillMaxHeight()
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(brush = gradientBrush)
                        )
                    }

                }
                Image(
                    painter = rememberAsyncImagePainter("$IMAGE_BASE_URL${movie.posterPath}"),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                        .clip(
                            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        )
                )
            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopEnd)
                    .background(color = Color(0xD2000000))
                    .clickable {
                        onClick(movie)
                    }
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(color = Color.LightGray)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(movie = moviePreviewMock) {}
}