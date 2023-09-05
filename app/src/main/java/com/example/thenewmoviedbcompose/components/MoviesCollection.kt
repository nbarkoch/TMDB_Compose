package com.example.thenewmoviedbcompose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thenewmoviedbcompose.model.Movie


@Composable
fun MoviesCollection(movies: List<Movie>,
                     isLoading: Boolean = false,
                     onDetailsClick: (movie: Movie) -> Unit,
                     onEndReached: () -> Unit) {
    val movieListState = rememberLazyListState()

    LazyColumn(
        state= movieListState,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()) {
        items(movies) { movie ->
            MovieCard(movie = movie, onClick = onDetailsClick)
        }
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    LaunchedEffect(movieListState.isAtBottom()) {
        onEndReached()
    }
}

@Composable
private fun LazyListState.isAtBottom(): Boolean {

    return remember(this) {
        derivedStateOf {
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.last()
                val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                        lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
            }
        }
    }.value
}


@Composable
@Preview
fun MoviesCollectionPreview(){
    val movies = listOf<Movie>(
//        Movie(
//        1,false,"", emptyArray<Int>(), "",
//    )
    )
}