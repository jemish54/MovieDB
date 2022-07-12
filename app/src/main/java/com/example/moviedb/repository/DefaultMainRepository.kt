package com.example.moviedb.repository

import com.example.moviedb.network.MovieApi
import com.example.moviedb.util.Resource
import com.example.moviedb.models.MovieResponse
import com.example.moviedb.models.UpcomingMovieResponse
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val movieApi: MovieApi
) : MainRepository {

    override suspend fun getPopular(): Resource<MovieResponse> {
        return try{
            val response = movieApi.getPopular()
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

    override suspend fun getTopRated(): Resource<MovieResponse> {
        return try{
            val response = movieApi.getTopRated()
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

    override suspend fun getUpcoming(): Resource<UpcomingMovieResponse> {
        return try{
            val response = movieApi.getUpcoming()
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