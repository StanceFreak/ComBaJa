package com.stancefreak.combaja.view.home

import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.collection.MutableVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stancefreak.combaja.BuildConfig
import com.stancefreak.combaja.data.repo.MovieRepository
import com.stancefreak.combaja.data.response.MoviesGenreList
import com.stancefreak.combaja.data.response.MoviesPopular
import com.stancefreak.combaja.data.response.MoviesPopularList
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val repository = MovieRepository()
    private val token = BuildConfig.ACCESS_TOKEN
    private val _popularMovie = MutableLiveData<ArrayList<MoviesPopular>>()
    val popularMovie: LiveData<ArrayList<MoviesPopular>> = _popularMovie

//    private val _movieList = MutableLiveData<MoviesPopularList>()
//    val movieList: LiveData<MoviesPopularList> = _movieList

    private val _genreList = MutableLiveData<MoviesGenreList>()
    val genreList: LiveData<MoviesGenreList> = _genreList

    private val _movieList = MutableLiveData<MoviesPopularList>()
    val movieList: LiveData<MoviesPopularList> = _movieList

    private val popularData = ArrayList<MoviesPopular>()
//    fun fetchPopularMovie() {
//        viewModelScope.launch {
//            try {
//                val popularResponse = repository.getPopularMovies()
//                val genreResponse = repository.getMoviesGenre("Bearer $token")
//                if (popularResponse.body() != null && genreResponse.body() != null) {
//                    popularData.apply {
//                        clear()
//                        add(MoviesPopular(genreResponse.body(), popularResponse.body()))
//                    }
//                    _popularMovie.postValue(popularData)
//                }
//            } catch (e: Exception) {
//                Log.e("network error", e.message.toString())
//            }
//        }
//    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val popularResponse = repository.getPopularMovies()
                if (popularResponse.body() != null) {
                    _movieList.postValue(popularResponse.body())
                }
            } catch (e: Exception) {
                Log.e("network error", e.message.toString())
            }
        }
    }

    fun fetchPopularGenres() {
        viewModelScope.launch {
            try {
                val genreResponse = repository.getMoviesGenre("Bearer $token")
                if (genreResponse.body() != null) {
                    _genreList.postValue(genreResponse.body())
                }
            } catch (e: Exception) {
                Log.e("network error", e.message.toString())
            }
        }
    }

    fun fetchMoviesByGenre(
        genreId: Int,
    ) {
        viewModelScope.launch {
            try {
                val moviesByGenreResponse = repository.getMoviesByGenre("Bearer $token", genreId, "popularity.desc")
                if (moviesByGenreResponse.body() != null) {
                    _movieList.postValue(moviesByGenreResponse.body())
                }
            } catch (e: Exception) {
                Log.e("network error", e.message.toString())
            }
        }
    }

}