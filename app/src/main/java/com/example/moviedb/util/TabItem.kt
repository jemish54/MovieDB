package com.example.moviedb.util

import androidx.compose.runtime.Composable
import com.example.moviedb.screens.MoviesScreen
import com.example.moviedb.screens.SeriesScreen
import com.example.moviedb.screens.WatchListScreen
import com.example.moviedb.viewmodels.MainViewModel

sealed class TabItem(val title: String, val content: @Composable () -> Unit) {
    class MovieScreenTab(viewModel: MainViewModel) :
        TabItem("Movies", { MoviesScreen(viewModel = viewModel) })

    class SeriesScreenTab(viewModel: MainViewModel) :
        TabItem("Series", { SeriesScreen(viewModel = viewModel) })

    class MyListScreenTab() :
        TabItem("Watchlist", { WatchListScreen() })
}
