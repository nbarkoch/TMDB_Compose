package com.example.thenewmoviedbcompose.ui.screens

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.example.thenewmoviedbcompose.MOVIE_ENTITY
import com.example.thenewmoviedbcompose.NavigationScreen
import com.example.thenewmoviedbcompose.ui.components.FilterCard
import com.example.thenewmoviedbcompose.ui.components.FilterCardSpacer
import com.example.thenewmoviedbcompose.ui.components.MoviesCollection
import com.example.thenewmoviedbcompose.ui.daoMock
import com.example.thenewmoviedbcompose.ui.popups.ErrorPopup
import com.example.thenewmoviedbcompose.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.Serializable


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val filterState = rememberLazyListState()
    val paginationMutex = remember { Mutex() }
    val activeFilterIndex = rememberSaveable { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
            .background(Color.White)
    ) {

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Movies", style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(end = 10.dp)
                )
                LazyRow(
                    state = filterState,
                    modifier = Modifier
                        .background(Color.White)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    itemsIndexed(viewModel.filters) { index, filter ->
                        FilterCard(filter.name, activeFilterIndex.intValue == index) {
                            activeFilterIndex.intValue = index
                            executeApiCall(
                                activeFilterIndex.intValue,
                                viewModel,
                                paginationMutex,
                                1
                            )
                        }
                        if (index < viewModel.filters.size - 1) {
                            FilterCardSpacer()
                        }
                    }
                }
            }
            MoviesCollection(viewModel.movies, onDetailsClick = { movie ->
                navigateWithSerializable(navController, MOVIE_ENTITY, movie)
            }, onEndReached = {
                if (!viewModel.isLoadingMovies.value) {
                    executeApiCall(
                        activeFilterIndex.intValue,
                        viewModel,
                        paginationMutex,
                        viewModel.nextPage.value
                    )
                }
            }, isLoading = viewModel.isLoadingMovies.value)
        }
        ErrorPopup(message = viewModel.errorMessage.value) {
            viewModel.clearErrorMessage()
        }
    }
    LaunchedEffect(activeFilterIndex.intValue) {
        filterState.animateScrollToItem(activeFilterIndex.intValue)
    }
}


fun executeApiCall(
    filterIndex: Int,
    viewModel: HomeViewModel,
    mutex: Mutex,
    pageNumber: Int
) {
    viewModel.viewModelScope.launch {
        mutex.withLock {
            viewModel.filters[filterIndex].fetchMovies(pageNumber)
        }
    }
}

fun navigateWithSerializable(
    navController: NavController,
    key: String,
    serializable: Serializable
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(NavigationScreen.Details.route).toUri())
        .build()
    navController.graph.matchDeepLink(routeLink)?.run {
        val bundle = Bundle()
        bundle.putSerializable(key, serializable)
        navController.navigate(destination.id, bundle)
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
        viewModel = HomeViewModel(daoMock())
    )
}