package com.example.moviedb.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.moviedb.screens.MoviesScreen
import com.example.moviedb.screens.SearchScreen
import com.example.moviedb.screens.SeriesScreen
import com.example.moviedb.screens.WatchListScreen
import com.example.moviedb.viewmodels.MainViewModel

sealed class TabItem(val title: String?,val icon:ImageVector?, val content: @Composable () -> Unit) {
    class MovieScreenTab(viewModel: MainViewModel) :
        TabItem("Movies",null, { MoviesScreen(viewModel = viewModel) })

    class SeriesScreenTab(viewModel: MainViewModel) :
        TabItem("Series",null, { SeriesScreen(viewModel = viewModel) })

    class MyListScreenTab() :
        TabItem("Watchlist",null, { WatchListScreen() })

    class SearchScreenTab(viewModel: MainViewModel) :
            TabItem(null,Icons.Default.Search,{ SearchScreen(viewModel) })

}