package com.example.moviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.repository.MainRepository
import com.example.moviedb.util.Resource
import com.james54.moviedatabase.models.MovieResponse
import com.james54.moviedatabase.models.UpcomingMovieResponse
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
        class Success(val movieList:MovieResponse): MovieStates()
        class SuccessUpcoming(val movieList: UpcomingMovieResponse): MovieStates()
        class Failure(val message:String): MovieStates()
        object Loading: MovieStates()
        object Empty: MovieStates()
    }

    private val _popularMovies = MutableStateFlow<MovieStates>(MovieStates.Empty)
    val popularMovies:StateFlow<MovieStates> = _popularMovies

    private val _topRatedMovies = MutableStateFlow<MovieStates>(MovieStates.Empty)
    val topRatedMovies:StateFlow<MovieStates> = _topRatedMovies

    private val _upcomingMovies = MutableStateFlow<MovieStates>(MovieStates.Empty)
    val upcomingMovies:StateFlow<MovieStates> = _upcomingMovies

    init {
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        _popularMovies.value = MovieStates.Loading
        when(val result = mainRepository.getPopular()){
            is Resource.Success->{
                _popularMovies.value = MovieStates.Success(result.data!!)
            }
            is Resource.Error-> _popularMovies.value = MovieStates.Failure(result.message!!)
        }
    }

    private fun getTopRatedMovies() = viewModelScope.launch {
        _topRatedMovies.value = MovieStates.Loading
        when(val result = mainRepository.getTopRated()){
            is Resource.Success->{
                _topRatedMovies.value = MovieStates.Success(result.data!!)
            }
            is Resource.Error-> _topRatedMovies.value = MovieStates.Failure(result.message!!)
        }
    }

    private fun getUpcomingMovies() = viewModelScope.launch {
        _upcomingMovies.value = MovieStates.Loading
        when(val result = mainRepository.getUpcoming()){
            is Resource.Success->{
                _upcomingMovies.value = MovieStates.SuccessUpcoming(result.data!!)
            }
            is Resource.Error-> _upcomingMovies.value = MovieStates.Failure(result.message!!)
        }
    }

}