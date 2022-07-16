package com.example.moviedb.repository

import androidx.lifecycle.LiveData
import com.example.moviedb.models.*
import com.example.moviedb.util.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getPopularMovies(): Resource<MovieResponse>

    suspend fun getTopRatedMovies():Resource<MovieResponse>

    suspend fun getUpcomingMovies():Resource<UpcomingMovieResponse>

    suspend fun getPopularSeries(): Resource<SeriesResponse>

    suspend fun getTopRatedSeries():Resource<SeriesResponse>

    suspend fun searchKeyword(query:String):Resource<SearchResponse>

    fun getWatchList():Resource<Flow<List<WatchItem>>>

    suspend fun insertWatchItem(watchItem: WatchItem)

    suspend fun removeWatchItem(itemId:Int)

}