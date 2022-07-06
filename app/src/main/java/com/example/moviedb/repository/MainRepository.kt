package com.example.moviedb.repository

import com.example.moviedb.util.Resource
import com.james54.moviedatabase.models.MovieResponse
import com.james54.moviedatabase.models.UpcomingMovieResponse

interface MainRepository {

    suspend fun getPopular(): Resource<MovieResponse>

    suspend fun getTopRated():Resource<MovieResponse>

    suspend fun getUpcoming():Resource<UpcomingMovieResponse>

}