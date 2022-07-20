package com.example.moviedb.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
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
import com.example.moviedb.viewmodels.MainViewModel

@Composable
fun MovieDetailScreen(viewModel: MainViewModel, navController: NavController) {
    val movie: Movie? = viewModel.movieDetail.collectAsState().value
    Scaffold(modifier = Modifier.fillMaxSize()) {
        movie?.let {
            DetailView(it, navController, viewModel)
        }
    }
}

@Composable
fun DetailView(movie: Movie, navController: NavController, viewModel: MainViewModel) {

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
            AsyncImage(
                model = "$IMAGE_BASE${movie.poster_path}",
                contentDescription = "Movie Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
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
                ChipText(text = "${movie.vote_average}", icon = Icons.Filled.Star)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    viewModel.insertWatchItem(
                        WatchItem(
                            itemId = movie.id,
                            type = true,
                            movie = movie
                        )
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
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
            Text(text = movie.title, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(12.dp))
            ExpandableText(text = movie.overview)
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
                color = Color.White,
                fontSize = fontSize,
            )
        }
    }
}























