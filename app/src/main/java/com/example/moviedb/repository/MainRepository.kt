package com.example.moviedb.repository

import com.example.moviedb.util.Resource
import com.example.moviedb.models.MovieResponse
import com.example.moviedb.models.Series
import com.example.moviedb.models.SeriesResponse
import com.example.moviedb.models.UpcomingMovieResponse

interface MainRepository {

    suspend fun getPopularMovies(): Resource<MovieResponse>

    suspend fun getTopRatedMovies():Resource<MovieResponse>

    suspend fun getUpcomingMovies():Resource<UpcomingMovieResponse>

    suspend fun getPopularSeries(): Resource<SeriesResponse>

    suspend fun getTopRatedSeries():Resource<SeriesResponse>

}