package com.example.moviedb.repository

import android.util.Log
import com.example.moviedb.util.Resource
import com.example.moviedb.models.MovieResponse
import com.example.moviedb.models.SearchResponse
import com.example.moviedb.models.SeriesResponse
import com.example.moviedb.models.UpcomingMovieResponse
import com.example.moviedb.network.EntertainmentApi
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val entertainmentApi: EntertainmentApi
) : MainRepository {

    override suspend fun getPopularMovies(): Resource<MovieResponse> {
        return try{
            val response = entertainmentApi.getPopularMovies()
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch(e:Exception){
            Resource.Error(e.message ?: "An Unknown Error Occurred")
        }
    }

    override suspend fun getTopRatedMovies(): Resource<MovieResponse> {
        return try{
            val response = entertainmentApi.getTopRatedMovies()
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch(e:Exception){
            Resource.Error(e.message ?: "An Unknown Error Occurred")
        }
    }

    override suspend fun getUpcomingMovies(): Resource<UpcomingMovieResponse> {
        return try{
            val response = entertainmentApi.getUpcomingMovies()
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch(e:Exception){
            Resource.Error(e.message ?: "An Unknown Error Occurred")
        }
    }

    override suspend fun getPopularSeries(): Resource<SeriesResponse> {
        return try{
            val response = entertainmentApi.getPopularSeries()
            val result = response.body()
            Log.d("SeriesListDisplay", result.toString())
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch(e:Exception){
            Resource.Error(e.message ?: "An Unknown Error Occurred")
        }
    }

    override suspend fun getTopRatedSeries(): Resource<SeriesResponse> {
        return try{
            val response = entertainmentApi.getTopRatedSeries()
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch(e:Exception){
            Resource.Error(e.message ?: "An Unknown Error Occurred")
        }
    }

    override suspend fun searchKeyword(query:String): Resource<SearchResponse> {
        return try{
            val response = entertainmentApi.searchKeyword(query = query)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch(e:Exception){
            Resource.Error(e.message ?: "An Unknown Error Occurred")
        }
    }

}