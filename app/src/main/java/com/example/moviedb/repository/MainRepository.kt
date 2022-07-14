package com.example.moviedb.repository

import com.example.moviedb.models.*
import com.example.moviedb.util.Resource

interface MainRepository {

    suspend fun getPopularMovies(): Resource<MovieResponse>

    suspend fun getTopRatedMovies():Resource<MovieResponse>

    suspend fun getUpcomingMovies():Resource<UpcomingMovieResponse>

    suspend fun getPopularSeries(): Resource<SeriesResponse>

    suspend fun getTopRatedSeries():Resource<SeriesResponse>

    suspend fun searchKeyword(query:String):Resource<SearchResponse>

}