package com.example.moviedb.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.moviedb.models.Series
import com.example.moviedb.screens.composables.SeriesCard
import com.example.moviedb.util.Constants
import com.example.moviedb.viewmodels.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun SeriesScreen(viewModel: MainViewModel) {
    PopularSeriesSection(viewModel = viewModel)
}

@Composable
fun TopRatedSection(viewModel: MainViewModel) {
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
            HorizontalPagerWithDecoration(seriesList = data.movieList.results)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithDecoration(seriesList: List<Series>) {
    HorizontalPager(
        count = seriesList.size,
        contentPadding = PaddingValues(horizontal = 100.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
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
fun PopularSeriesSection(viewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState();
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
                state = listState,
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
                    TopRatedSection(viewModel = viewModel)
                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        text = "Top rated TV",
                        style = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.padding(20.dp),
                    )
                }
                items(items = data.movieList.results) {
                    SeriesCard(series = it)
                }
            }
        }
    }
    SideEffect {
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }
}