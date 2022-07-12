package com.example.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviedb.models.Movie
import com.example.moviedb.ui.theme.MovieDBTheme
import com.example.moviedb.util.Constants.Companion.IMAGE_BASE
import com.example.moviedb.util.TabItem
import com.example.moviedb.viewmodels.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            MovieDBTheme {
                val tabs = listOf(
                    TabItem.MovieScreen(viewModel = viewModel),
                    TabItem.SeriesScreen(viewModel = viewModel),
                    TabItem.MyListScreen(),
                )
                val pagerState = rememberPagerState()
                Scaffold {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TabNavigation(tabs = tabs, pagerState = pagerState)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabNavigation(tabs: List<TabItem>, pagerState: PagerState) {
    Column(modifier = Modifier.fillMaxSize()) {
        TabIndicator(tabs = tabs, pagerState = pagerState)
        HorizontalPager(count = tabs.size, state = pagerState) { page ->
            tabs[page].content()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabIndicator(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, tab ->
            Text(
                modifier = Modifier.clickable { scope.launch { pagerState.animateScrollToPage(index) } },
                text = tab.title,
                color =
                    if (pagerState.currentPage == index) Color.Red
                    else MaterialTheme.colors.onBackground,
            )
        }
    }
}

@Composable
fun MoviesScreen(viewModel: MainViewModel) {
    MoviesList(viewModel = viewModel)
}

@Composable
fun SeriesScreen(viewModel: MainViewModel) {
    Text("SERIES SCREEN", modifier = Modifier.fillMaxSize())
}

@Composable
fun WatchList() {
    Text("WATCH LIST", modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesList(viewModel: MainViewModel) {
    when (val data = viewModel.popularMovies.collectAsState().value) {
        MainViewModel.MovieStates.Empty -> {}
        is MainViewModel.MovieStates.Failure -> {
            Box(contentAlignment = Alignment.Center) {
                Text(data.message)
            }
        }
        MainViewModel.MovieStates.Loading -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is MainViewModel.MovieStates.Success -> {
            LazyVerticalGrid(
                contentPadding = PaddingValues(20.dp),
                cells = GridCells.Adaptive(180.dp)
            ) {
                items(items = data.movieList.results) {
                    MovieCard(it)
                }
            }
        }
        //Only for Upcoming(Not Released) movie list
        is MainViewModel.MovieStates.SuccessUpcoming -> {}
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            model = "$IMAGE_BASE${movie.poster_path}",
            contentDescription = "Movie Thumbnail"
        )
    }
}

