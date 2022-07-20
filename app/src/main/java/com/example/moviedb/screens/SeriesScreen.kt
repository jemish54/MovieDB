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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviedb.models.Series
import com.example.moviedb.screens.composables.SeriesCard
import com.example.moviedb.util.Constants
import com.example.moviedb.util.NavScreen
import com.example.moviedb.viewmodels.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@Composable
fun SeriesScreen(viewModel: MainViewModel,navController: NavController) {
    PopularSeriesSection(viewModel = viewModel,navController)
}

@Composable
fun TopRatedSection(viewModel: MainViewModel,navController: NavController) {
    when (val data = viewModel.popularSeries.collectAsState().value) {
        MainViewModel.SeriesStates.Empty -> {}
        is MainViewModel.SeriesStates.Failure -> {
            Box(contentAlignment = Alignment.Center) {
                Text(data.message)
            }
        }
        MainViewModel.SeriesStates.Loading -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is MainViewModel.SeriesStates.Success -> {
            HorizontalPagerWithDecoration(seriesList = data.seriesList.results,viewModel, navController)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithDecoration(seriesList: List<Series>,viewModel: MainViewModel,navController: NavController) {
    HorizontalPager(
        count = seriesList.size,
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
                .clickable {
                    viewModel.setSeriesDetails(seriesList[page])
                    navController.navigate(NavScreen.SeriesDetailScreen.route)
                }
        ) {
            AsyncImage(
                model = "${Constants.IMAGE_BASE}${seriesList[page].poster_path}",
                contentDescription = "Movie thumbnail",
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularSeriesSection(viewModel: MainViewModel,navController: NavController) {
    when (val data = viewModel.topRatedSeries.collectAsState().value) {
        MainViewModel.SeriesStates.Empty -> {}
        is MainViewModel.SeriesStates.Failure -> {
            Box(contentAlignment = Alignment.Center) {
                Text(data.message)
            }
        }
        MainViewModel.SeriesStates.Loading -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is MainViewModel.SeriesStates.Success -> {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        text = "Popular Shows",
                        style = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.padding(20.dp),
                    )
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    TopRatedSection(viewModel = viewModel,navController)
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        text = "Top rated TV",
                        style = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.padding(20.dp),
                    )
                }
                items(items = data.seriesList.results) {
                    SeriesCard(series = it){
                        viewModel.setSeriesDetails(it)
                        navController.navigate(NavScreen.SeriesDetailScreen.route)
                    }
                }
            }
        }
    }
}