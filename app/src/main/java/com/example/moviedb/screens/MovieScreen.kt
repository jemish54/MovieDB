package com.example.moviedb.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.lerp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviedb.models.Movie
import com.example.moviedb.models.WatchItem
import com.example.moviedb.screens.composables.MovieCard
import com.example.moviedb.util.Constants.Companion.IMAGE_BASE
import com.example.moviedb.util.NavScreen
import com.example.moviedb.viewmodels.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@Composable
fun MoviesScreen(viewModel: MainViewModel,navController: NavController) {
    PopularMoviesSection(viewModel = viewModel,navController)
}

@Composable
fun ComingSoonSection(viewModel: MainViewModel,navController: NavController) {
    when (val data = viewModel.upcomingMovies.collectAsState().value) {
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
        is MainViewModel.MovieStates.Success -> {}
        //Only for Upcoming(Not Released) movie list
        is MainViewModel.MovieStates.SuccessUpcoming -> {
            HorizontalPagerWithDecoration(upcomingMovieList = data.movieList.results, navController = navController, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithDecoration(upcomingMovieList: List<Movie>,viewModel: MainViewModel,navController: NavController) {
    HorizontalPager(
        count = upcomingMovieList.size,
        contentPadding = PaddingValues(horizontal = 100.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    ) { page ->
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .clickable{
                    viewModel.setMovieDetails(upcomingMovieList[page])
                    navController.navigate(NavScreen.MovieDetailScreen.route)
                }
        ) {
            AsyncImage(
                model = "$IMAGE_BASE${upcomingMovieList[page].poster_path}",
                contentDescription = "Movie thumbnail",
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMoviesSection(viewModel: MainViewModel,navController: NavController) {
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
                cells = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        text = "Coming Soon",
                        style = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.padding(20.dp),
                    )
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    ComingSoonSection(viewModel = viewModel,navController)
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        text = "Trending Now",
                        style = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.padding(20.dp),
                    )
                }
                items(items = data.movieList.results) {
                    MovieCard(movie = it) {
                        viewModel.setMovieDetails(it)
                        navController.navigate(NavScreen.MovieDetailScreen.route)
                    }
                }
            }
        }
        //Only for Upcoming(Not Released) movie list
        is MainViewModel.MovieStates.SuccessUpcoming -> {}
    }
}
