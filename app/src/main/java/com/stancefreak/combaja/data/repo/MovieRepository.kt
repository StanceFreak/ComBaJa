package com.stancefreak.combaja.data.repo

import com.stancefreak.combaja.data.response.MoviesGenreList
import com.stancefreak.combaja.data.response.MoviesPopularList
import com.stancefreak.combaja.network.NetworkInstances
import retrofit2.Response
import retrofit2.http.Header

class MovieRepository {
    private val service = NetworkInstances.movieService
    suspend fun getPopularMovies(): Response<MoviesPopularList> {
        return service.getPopularMovies()
    }
    suspend fun getMoviesGenre(
        token: String,
    ): Response<MoviesGenreList> {
        return service.getMoviesGenre(token)
    }

    suspend fun getMoviesByGenre(
        token: String,
        genreId: Int,
        sortBy: String
    ): Response<MoviesPopularList> {
        return service.getMoviesByGenre(token, genreId, sortBy)
    }

}