package com.example.moviedb.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviedb.models.Movie
import com.example.moviedb.models.WatchItem
import com.example.moviedb.ui.theme.MovieDBTheme
import com.example.moviedb.util.Constants.Companion.IMAGE_BASE
import com.example.moviedb.util.genreDB
import com.example.moviedb.viewmodels.MainViewModel

@Composable
fun MovieDetailScreen(viewModel: MainViewModel, navController: NavController) {
    val movie: Movie? = viewModel.movieDetail.collectAsState().value
    movie?.let {
        DetailView(it, navController, viewModel)
    }

}

@Composable
fun DetailView(movie: Movie, navController: NavController, viewModel: MainViewModel) {

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        var inWatchList by remember {
            mutableStateOf(movie.in_watchlist)
        }
        FloatingActionButton(backgroundColor = Color.Red,onClick = {
            if (inWatchList) {
                viewModel.removeWatchItem(movie.id)
            } else {
                viewModel.insertWatchItem(
                    WatchItem(
                        itemId = movie.id,
                        type = true,
                        movie = movie
                    )
                )
            }
            movie.in_watchlist = !movie.in_watchlist
            inWatchList = !inWatchList
        }) {
            Icon(
                imageVector = if (inWatchList) Icons.Default.Delete else Icons.Default.Add,
                contentDescription = "Add to WatchList",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colors.surface
            )
        }
    }) {

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
                movie.poster_path?.let {
                    AsyncImage(
                        model = "$IMAGE_BASE${it}",
                        contentDescription = "Movie Poster",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                } ?: Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    Text("No Poster Available", color = MaterialTheme.colors.onSurface)
                }

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(48.dp).background(
                            color = Color.Red,
                            shape = RoundedCornerShape(6.dp),
                        ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Arrow",
                            modifier = Modifier.padding(6.dp),
                            tint = MaterialTheme.colors.surface
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp)
                ) {
                    ChipText(text = "${movie.vote_average}", icon = Icons.Filled.Star)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                movie.genre_ids.forEach {
                    ChipText(text = "${genreDB[it]}")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(text = movie.title, fontSize = 22.sp)
                Spacer(modifier = Modifier.height(12.dp))
                ExpandableText(text = movie.overview)
            }
        }

    }
}

@Composable
fun ExpandableText(text: String) {
    var showMore by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .animateContentSize(animationSpec = tween(durationMillis = 800))
        .clickable { showMore = !showMore }) {
        if (showMore) {
            Text(text = text)
        } else {
            Text(text = text, maxLines = 4, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun ChipText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.surface,
    fontSize: TextUnit = 16.sp,
    padding: Dp = 8.dp,
    icon: ImageVector? = null
) {
    Card(
        modifier = modifier,
        backgroundColor = color,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = padding),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = "Rating",
                    tint = Color.Yellow,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text,
                color = MaterialTheme.colors.onSurface,
                fontSize = fontSize,
            )
        }
    }
}





















