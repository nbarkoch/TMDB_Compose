package com.example.thenewmoviedbcompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.thenewmoviedbcompose.api.MoviesRetrofitInstance
import com.example.thenewmoviedbcompose.model.Movie
import retrofit2.Response
import java.io.IOException

class HomeViewModel: ViewModel() {

    data class FilterItem(
        val name: String,
        val invokeApiCall: suspend (page: Int) -> Unit
    )

    private val _movies = mutableStateListOf<Movie>()
    val movies: SnapshotStateList<Movie> = _movies
    private val _isLoadingMovies = mutableStateOf(false)
    val isLoadingMovies: MutableState<Boolean> = _isLoadingMovies


    val filters = listOf(
        FilterItem("Popular") {page ->
            if (page < 2){
                _movies.clear()
            }
            _isLoadingMovies.value = true
            val response = try {
                MoviesRetrofitInstance.api.getPopular(page = page)
            } catch (e: IOException) {
                // internet connection
                e.printStackTrace()
                return@FilterItem
            } catch (e: Exception) {
                // unexpected response, server/library errors
                e.printStackTrace()
                return@FilterItem
            } finally {
                _isLoadingMovies.value = false
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    _movies.addAll(it.results)
                }
            } else {

            }
        },
        FilterItem("Favorite") {
            _movies.clear()

        },
        FilterItem("Now Playing") {page ->
            if (page < 2){
                _movies.clear()
            }
            val response = try {
                MoviesRetrofitInstance.api.getNowPlaying(page = page)
            } catch (e: IOException) {
                // internet connection
                e.printStackTrace()
                return@FilterItem
            } catch (e: Exception) {
                // unexpected response, server/library errors
                e.printStackTrace()
                return@FilterItem
            } finally {
                _isLoadingMovies.value = false
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    _movies.addAll(it.results)
                }
            } else {

            }
        }
    )



}