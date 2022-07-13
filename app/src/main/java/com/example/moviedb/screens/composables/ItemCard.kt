package com.example.moviedb.screens.composables

import android.hardware.camera2.params.ColorSpaceTransform
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.moviedb.models.Movie
import com.example.moviedb.models.Series
import com.example.moviedb.util.Constants
import java.nio.file.WatchEvent

@Composable
fun MovieCard(movie: Movie) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Box(contentAlignment = Alignment.TopStart) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                model = "${Constants.IMAGE_BASE}${movie.poster_path}",
                contentDescription = "Movie Thumbnail"
            )
            Text(
                text = "${movie.vote_average}",
                modifier = Modifier
                    .offset(8.dp, 8.dp)
                    .background(MaterialTheme.colors.surface.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp),
                style = TextStyle(fontSize = 14.sp),
            )
        }
    }
}

@Composable
fun SeriesCard(series: Series) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Box(contentAlignment = Alignment.TopStart) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                model = "${Constants.IMAGE_BASE}${series.poster_path}",
                contentDescription = "Movie Thumbnail"
            )
            Text(
                text = "${series.vote_average}",
                modifier = Modifier
                    .offset(8.dp, 8.dp)
                    .background(MaterialTheme.colors.surface.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp),
                style = TextStyle(fontSize = 14.sp),
            )
        }
    }
}