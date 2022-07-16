package com.example.moviedb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.moviedb.models.*
import com.example.moviedb.util.Resource
import com.example.moviedb.network.EntertainmentApi
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val entertainmentApi: EntertainmentApi,
    private val watchItemDao:WatchItemDao
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

    override fun getWatchList(): Resource<Flow<List<WatchItem>>> {
        return try{
            val result = watchItemDao.getAllWatchItems()
            Resource.Success(result)
        }catch (e:Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun insertWatchItem(watchItem: WatchItem) {
        watchItemDao.insertItem(watchItem)
    }

    override suspend fun removeWatchItem(itemId:Int) {
        watchItemDao.removeItem(itemId)
    }


}