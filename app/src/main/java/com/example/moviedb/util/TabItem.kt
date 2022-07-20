package com.example.moviedb.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.moviedb.screens.MoviesScreen
import com.example.moviedb.screens.SearchScreen
import com.example.moviedb.screens.SeriesScreen
import com.example.moviedb.screens.WatchListScreen
import com.example.moviedb.viewmodels.MainViewModel

sealed class TabItem(val title: String?,val icon:ImageVector?, val content: @Composable () -> Unit) {
    class MovieScreenTab(viewModel: MainViewModel,navController: NavController) :
        TabItem("Movies",null, { MoviesScreen(viewModel,navController) })

    class SeriesScreenTab(viewModel: MainViewModel,navController: NavController) :
        TabItem("Series",null, { SeriesScreen(viewModel,navController) })

    class MyListScreenTab(viewModel: MainViewModel,navController: NavController) :
        TabItem("Watchlist",null, { WatchListScreen(viewModel,navController) })

    class SearchScreenTab(viewModel: MainViewModel,navController: NavController) :
            TabItem(null,Icons.Default.Search,{ SearchScreen(viewModel,navController) })

}