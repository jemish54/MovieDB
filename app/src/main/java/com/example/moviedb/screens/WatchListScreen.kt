package com.example.moviedb.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviedb.screens.composables.MovieCard
import com.example.moviedb.screens.composables.SeriesCard
import com.example.moviedb.util.NavScreen
import com.example.moviedb.viewmodels.MainViewModel

@Composable
fun WatchListScreen(viewModel: MainViewModel,navController: NavController) {
    WatchListSection(viewModel,navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WatchListSection(viewModel: MainViewModel,navController: NavController) {
    when (val data = viewModel.watchList.collectAsState().value) {
        MainViewModel.WatchListStates.Empty -> {}
        is MainViewModel.WatchListStates.Failure -> {
            Box(contentAlignment = Alignment.Center) {
                Text(data.message)
            }
        }
        MainViewModel.WatchListStates.Loading -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is MainViewModel.WatchListStates.Success -> {
            val watchlist = data.watchList.collectAsState(initial = mutableListOf()).value
            if (watchlist.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Add Something")
                }
            }else{
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    cells = GridCells.Adaptive(150.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = watchlist) {
                        if(it.type) MovieCard(movie = it.movie!!){
                            viewModel.removeWatchItem(it.itemId)
                        }
                        else SeriesCard(series = it.series!!){
                            viewModel.removeWatchItem(it.itemId)
                        }
                    }
                }
            }
        }
    }
}