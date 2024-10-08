package com.stancefreak.combaja.network

import com.stancefreak.combaja.BuildConfig
import com.stancefreak.combaja.data.response.MoviesGenreList
import com.stancefreak.combaja.data.response.MoviesPopularList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getPopularMovies(): Response<MoviesPopularList>

    @GET("genre/movie/list")
    suspend fun getMoviesGenre(
        @Header("Authorization") token: String,
    ): Response<MoviesGenreList>

    @GET("discover/movie?")
    suspend fun getMoviesByGenre(
        @Header("Authorization") token: String,
        @Query("with_genres") genreId: Int,
        @Query("sort_by") sortBy: String
    ): Response<MoviesPopularList>

}