package com.example.moviedb.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviedb.models.Movie
import com.example.moviedb.models.Series
import com.example.moviedb.models.WatchItem
import com.example.moviedb.util.Constants
import com.example.moviedb.viewmodels.MainViewModel

@Composable
fun SeriesDetailScreen(viewModel: MainViewModel,navController: NavController){
    val series = viewModel.seriesDetail.collectAsState().value
    Scaffold() {
        series?.let {
            DetailView(it, navController, viewModel)
        }
    }
}

@Composable
fun DetailView(series: Series, navController: NavController, viewModel: MainViewModel) {

    var inWatchList by remember {
        mutableStateOf(series.in_watchlist)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f),
        ) {
            series.poster_path?.let {
                AsyncImage(
                    model = "${Constants.IMAGE_BASE}${it}",
                    contentDescription = "Movie Poster",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            } ?: Box(
                modifier = Modifier.fillMaxSize().align(Alignment.Center)
            ){
                Text("No Poster Available")
            }
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(12.dp)
            ) {
                Box(
                    modifier = Modifier.background(
                        color = Color.Red.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(6.dp),
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Arrow",
                        modifier = Modifier.padding(6.dp),
                        tint = Color.White
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ChipText(
                    text = "18+",
                )
                ChipText(text = "Action")
                ChipText(text = "${series.vote_average}", icon = Icons.Filled.Star)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if(inWatchList){
                        viewModel.removeWatchItem(series.id)
                    }else{
                        viewModel.insertWatchItem(
                            WatchItem(
                                itemId = series.id,
                                type = false,
                                series = series
                            )
                        )
                    }
                    series.in_watchlist = !series.in_watchlist
                    inWatchList = !inWatchList
                }) {
                    Icon(
                        imageVector = if(inWatchList) Icons.Default.Delete else Icons.Default.AddCircle,
                        contentDescription = "Add to WatchList"
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(text = series.name, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(12.dp))
            ExpandableText(text = series.overview)
        }
    }

}