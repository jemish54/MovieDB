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
                    TabItem.MovieScreenTab(viewModel = viewModel),
                    TabItem.SeriesScreenTab(viewModel = viewModel),
                    TabItem.MyListScreenTab(),
                    TabItem.SearchScreenTab(viewModel = viewModel),
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.clickable { scope.launch { pagerState.animateScrollToPage(index) } },
            ) {
                tab.icon?.let {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = it, contentDescription = "Search Icon",
                        tint =
                        if (pagerState.currentPage == index) Color.Red
                        else MaterialTheme.colors.onBackground,
                    )
                }
                tab.title?.let {
                    Text(
                        text = it,
                        color =
                        if (pagerState.currentPage == index) Color.Red
                        else MaterialTheme.colors.onBackground,
                    )
                }
            }
        }
    }
}

