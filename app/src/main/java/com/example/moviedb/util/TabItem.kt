package com.example.moviedb.util

import androidx.compose.runtime.Composable
import com.example.moviedb.MoviesScreen
import com.example.moviedb.WatchList
import com.example.moviedb.viewmodels.MainViewModel

sealed class TabItem(val title: String, val content: @Composable () -> Unit) {
    class MovieScreen(viewModel: MainViewModel) :
        TabItem("Movies", { MoviesScreen(viewModel = viewModel) })

    class SeriesScreen(viewModel: MainViewModel) :
        TabItem("Series", { com.example.moviedb.SeriesScreen(viewModel = viewModel) })

    class MyListScreen() :
            TabItem("Watchlist", { WatchList() })
}
