package com.example.moviedb.util

import com.example.moviedb.viewmodels.MainViewModel

sealed class NavScreen(val route:String){
    object MainScreen:NavScreen("main_screen")
    object MovieDetailScreen:NavScreen("movie_detail_screen")
    object SeriesDetailScreen:NavScreen("series_detail_screen")
}
