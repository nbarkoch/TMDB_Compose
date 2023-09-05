package com.example.thenewmoviedbcompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.thenewmoviedbcompose.api.MoviesRetrofitInstance
import com.example.thenewmoviedbcompose.model.Movie
import com.example.thenewmoviedbcompose.storage.FavoriteMovieDao
import retrofit2.Response
import java.io.IOException

class HomeViewModel(
    private val favoriteMovieDao: FavoriteMovieDao
) : ViewModel() {

    data class FilterItem(
        val name: String,
        val invokeApiCall: suspend (page: Int) -> Unit
    )

    private val _movies = mutableStateListOf<Movie>()
    val movies: SnapshotStateList<Movie> = _movies
    private val _isLoadingMovies = mutableStateOf(false)
    val isLoadingMovies: MutableState<Boolean> = _isLoadingMovies

    private val _activeFilterIndex = mutableIntStateOf(0)
//    val activeFilterIndex: MutableState<Int> = _activeFilterIndex
//    fun setActiveFilterIndex(index: Int){
//        _activeFilterIndex.intValue = index
//    }

    val filters = listOf(
        FilterItem("Popular") { page ->
            if (page < 2) {
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
        FilterItem("Favorite") { page ->
            if (page < 2) {
                _movies.clear()
            }
            _isLoadingMovies.value = true
            val favoriteMovies = try {
                favoriteMovieDao.getMovies(10, page)
            } catch (e: Exception) {
                e.printStackTrace()
                return@FilterItem
            } finally {
                _isLoadingMovies.value = false
            }
            _movies.addAll(favoriteMovies.filter { !_movies.contains(it) })
        },
        FilterItem("Now Playing") { page ->
            if (page < 2) {
                _movies.clear()
            }
            _isLoadingMovies.value = true
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