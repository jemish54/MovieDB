package com.example.moviedb.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviedb.models.Movie
import com.example.moviedb.models.Result
import com.example.moviedb.models.Series
import com.example.moviedb.util.Constants

val ratio:Float = 0.67f

@Composable
fun MovieCard(movie:Movie,onClick:()->Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp).clickable {
            onClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio),
        ) {
            AsyncImage(
                contentScale = ContentScale.FillWidth,
                model = "${Constants.IMAGE_BASE}${movie.poster_path}",
                contentDescription = "Movie Thumbnail"
            )
            Text(
                text = "${movie.vote_average}",
                modifier = Modifier
                    .offset(8.dp, 8.dp)
                    .background(
                        MaterialTheme.colors.surface.copy(alpha = 0.8f),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp),
                style = TextStyle(fontSize = 14.sp),
            )
        }
    }
}

@Composable
fun SeriesCard(series: Series,onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp).clickable {
            onClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio),
        ) {
            AsyncImage(
                contentScale = ContentScale.FillWidth,
                model = "${Constants.IMAGE_BASE}${series.poster_path}",
                contentDescription = "Movie Thumbnail"
            )
            Text(
                text = "${series.vote_average}",
                modifier = Modifier
                    .offset(8.dp, 8.dp)
                    .background(
                        MaterialTheme.colors.surface.copy(alpha = 0.8f),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp),
                style = TextStyle(fontSize = 14.sp),
            )
        }
    }
}

@Composable
fun SearchResultCard(searchResult: Result,onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp).clickable {
            onClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio),
        ) {
            AsyncImage(
                contentScale = ContentScale.FillWidth,
                model = "${Constants.IMAGE_BASE}${searchResult.poster_path}",
                contentDescription = "Movie Thumbnail"
            )
            Text(
                text = "${searchResult.vote_average}",
                modifier = Modifier
                    .offset(8.dp, 8.dp)
                    .background(
                        MaterialTheme.colors.surface.copy(alpha = 0.8f),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp),
                style = TextStyle(fontSize = 14.sp),
            )
        }
    }
}