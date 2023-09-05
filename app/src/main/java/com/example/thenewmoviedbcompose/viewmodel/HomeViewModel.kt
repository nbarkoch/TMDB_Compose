package com.example.thenewmoviedbcompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
        val fetchMovies: suspend (page: Int) -> Unit
    )

    private val _movies = mutableStateListOf<Movie>()
    val movies: SnapshotStateList<Movie> = _movies
    private val _isLoadingMovies = mutableStateOf(false)
    val isLoadingMovies: MutableState<Boolean> = _isLoadingMovies

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: MutableState<String?> = _errorMessage
    fun clearErrorMessage() {
        _errorMessage.value = null
    }


    private val _nextPage = mutableIntStateOf(1)
    val nextPage: MutableState<Int> = _nextPage

    val filters = listOf(
        FilterItem("Popular") { page ->
            invokeCall({
                MoviesRetrofitInstance.api.getPopular(page = page)
            }, {
                it.results
            }, page)
        },
        FilterItem("Favorite") { offset ->
            invokeCall({
                Response.success(favoriteMovieDao.getMovies(5, offset - 1))
            }, { movies ->
                movies.filter { !_movies.contains(it) }
            }, offset)
        },
        FilterItem("Now Playing") { page ->
            invokeCall({
                MoviesRetrofitInstance.api.getNowPlaying(page = page)
            }, {
                it.results
            }, page)
        }
    )


    private suspend fun <T> invokeCall(
        retrofitCall: suspend (page: Int) -> Response<T>,
        extractResults: (res: T) -> List<Movie>,
        page: Int
    ) {
        if (page < 2) {
            _movies.clear()
        }
        _isLoadingMovies.value = true
        val response = try {
            retrofitCall(page)
        } catch (e: IOException) {
            _errorMessage.value = "Connection Failure\nCheck if device is connected.."
            e.printStackTrace()
            return
        } catch (e: Exception) {
            _errorMessage.value = "Unexpected Error Occurred\nTry again later.."
            e.printStackTrace()
            return
        } finally {
            _isLoadingMovies.value = false
        }
        if (response.isSuccessful) {
            response.body()?.let { body ->
                extractResults(body).let { newMovies ->
                    if (newMovies.isNotEmpty()) {
                        _movies.addAll(newMovies)
                        _nextPage.intValue = page + 1
                    }
                }
            }
        } else {
            _errorMessage.value = "Unexpected Error Occurred\nTry again later.."
        }
    }
}