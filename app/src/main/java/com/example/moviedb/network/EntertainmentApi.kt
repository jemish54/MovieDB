package com.example.moviedb.network

import com.example.moviedb.util.Constants.Companion.API_KEY
import com.example.moviedb.models.MovieResponse
import com.example.moviedb.models.SeriesResponse
import com.example.moviedb.models.UpcomingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EntertainmentApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        api_key: String = API_KEY
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        api_key: String = API_KEY
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        api_key: String = API_KEY
    ): Response<UpcomingMovieResponse>

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key")
        api_key: String = API_KEY
    ): Response<SeriesResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(
        @Query("api_key")
        api_key: String = API_KEY
    ): Response<SeriesResponse>

}