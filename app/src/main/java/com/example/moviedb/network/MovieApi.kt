package com.example.moviedb.network

import com.example.moviedb.util.Constants.Companion.API_KEY
import com.james54.moviedatabase.models.MovieResponse
import com.james54.moviedatabase.models.UpcomingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("popular")
    suspend fun getPopular(
        @Query("api_key")
        api_key:String = API_KEY
    ):Response<MovieResponse>

    @GET("top_rated")
    suspend fun getTopRated(
        @Query("api_key")
        api_key:String = API_KEY
    ):Response<MovieResponse>

    @GET("upcoming")
    suspend fun getUpcoming(
        @Query("api_key")
        api_key:String = API_KEY
    ):Response<UpcomingMovieResponse>

}