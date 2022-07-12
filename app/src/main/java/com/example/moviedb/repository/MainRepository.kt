package com.example.moviedb.repository

import com.example.moviedb.util.Resource
import com.example.moviedb.models.MovieResponse
import com.example.moviedb.models.UpcomingMovieResponse

interface MainRepository {

    suspend fun getPopular(): Resource<MovieResponse>

    suspend fun getTopRated():Resource<MovieResponse>

    suspend fun getUpcoming():Resource<UpcomingMovieResponse>

}