package com.example.moviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.repository.MainRepository
import com.example.moviedb.util.Resource
import com.example.moviedb.models.MovieResponse
import com.example.moviedb.models.Series
import com.example.moviedb.models.SeriesResponse
import com.example.moviedb.models.UpcomingMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    sealed class MovieStates{
        class Success(val movieList: MovieResponse): MovieStates()
        class SuccessUpcoming(val movieList: UpcomingMovieResponse): MovieStates()
        class Failure(val message:String): MovieStates()
        object Loading: MovieStates()
        object Empty: MovieStates()
    }

    sealed class SeriesStates{
        class Success(val movieList: SeriesResponse): SeriesStates()
        class Failure(val message:String): SeriesStates()
        object Loading: SeriesStates()
        object Empty: SeriesStates()
    }

    private val _popularMovies = MutableStateFlow<MovieStates>(MovieStates.Empty)
    val popularMovies:StateFlow<MovieStates> = _popularMovies

    private val _topRatedMovies = MutableStateFlow<MovieStates>(MovieStates.Empty)
    val topRatedMovies:StateFlow<MovieStates> = _topRatedMovies

    private val _upcomingMovies = MutableStateFlow<MovieStates>(MovieStates.Empty)
    val upcomingMovies:StateFlow<MovieStates> = _upcomingMovies

    private val _popularSeries = MutableStateFlow<SeriesStates>(SeriesStates.Empty)
    val popularSeries:StateFlow<SeriesStates> = _popularSeries

    private val _topRatedSeries = MutableStateFlow<SeriesStates>(SeriesStates.Empty)
    val topRatedSeries:StateFlow<SeriesStates> = _topRatedSeries

    init {
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
        getPopularSeries()
        getTopRatedSeries()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        _popularMovies.value = MovieStates.Loading
        when(val result = mainRepository.getPopularMovies()){
            is Resource.Success->{
                _popularMovies.value = MovieStates.Success(result.data!!)
            }
            is Resource.Error-> _popularMovies.value = MovieStates.Failure(result.message!!)
        }
    }

    private fun getTopRatedMovies() = viewModelScope.launch {
        _topRatedMovies.value = MovieStates.Loading
        when(val result = mainRepository.getTopRatedMovies()){
            is Resource.Success->{
                _topRatedMovies.value = MovieStates.Success(result.data!!)
            }
            is Resource.Error-> _topRatedMovies.value = MovieStates.Failure(result.message!!)
        }
    }

    private fun getUpcomingMovies() = viewModelScope.launch {
        _upcomingMovies.value = MovieStates.Loading
        when(val result = mainRepository.getUpcomingMovies()){
            is Resource.Success->{
                _upcomingMovies.value = MovieStates.SuccessUpcoming(result.data!!)
            }
            is Resource.Error-> _upcomingMovies.value = MovieStates.Failure(result.message!!)
        }
    }

    private fun getPopularSeries() = viewModelScope.launch {
        _popularSeries.value = SeriesStates.Loading
        when(val result = mainRepository.getPopularSeries()){
            is Resource.Success->{
                _popularSeries.value = SeriesStates.Success(result.data!!)
            }
            is Resource.Error-> _popularSeries.value = SeriesStates.Failure(result.message!!)
        }
    }

    private fun getTopRatedSeries() = viewModelScope.launch {
        _topRatedSeries.value = SeriesStates.Loading
        when(val result = mainRepository.getTopRatedSeries()){
            is Resource.Success->{
                _topRatedSeries.value = SeriesStates.Success(result.data!!)
            }
            is Resource.Error-> _topRatedSeries.value = SeriesStates.Failure(result.message!!)
        }
    }

}