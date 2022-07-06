package com.example.moviedb

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.moviedb.ui.theme.MovieDBTheme
import com.example.moviedb.util.Constants.Companion.IMAGE_BASE
import com.example.moviedb.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.nio.file.WatchEvent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieDBTheme {
                val viewModel:MainViewModel by viewModels()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MoviesList(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MoviesList(viewModel : MainViewModel){
    when(val data = viewModel.popularMovies.collectAsState().value){
        MainViewModel.MovieStates.Empty -> {}
        is MainViewModel.MovieStates.Failure -> {
            Box(contentAlignment = Alignment.Center){
                Text(data.message)
            }
        }
        MainViewModel.MovieStates.Loading -> {
            Box(contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        is MainViewModel.MovieStates.Success -> {
            LazyColumn{
                items(items = data.movieList.results){
                    Card(modifier = Modifier.padding(5.dp)) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)) {
                            Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.width(150.dp)) {
                                AsyncImage(model = "$IMAGE_BASE${it.poster_path}", contentDescription = "MovieThumbnail")
                            }
                            Text(it.title)
                        }
                    }
                }
            }
        }
        is MainViewModel.MovieStates.SuccessUpcoming -> {}
    }
}
