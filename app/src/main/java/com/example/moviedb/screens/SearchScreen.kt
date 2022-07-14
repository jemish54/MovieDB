package com.example.moviedb.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moviedb.screens.composables.SearchResultCard
import com.example.moviedb.viewmodels.MainViewModel

@Composable
fun SearchScreen(viewModel:MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar{ before,after->
            if(after.length>before.length && after.isNotEmpty()){
                viewModel.searchKeyword(after)
            }
        }
        SearchResults(viewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResults(viewModel:MainViewModel) {
    when (val data = viewModel.searchKeyword.collectAsState().value) {
        MainViewModel.SearchStates.Empty -> {}
        is MainViewModel.SearchStates.Failure -> {
            Box(contentAlignment = Alignment.Center) {
                Text(data.message)
            }
        }
        MainViewModel.SearchStates.Loading -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is MainViewModel.SearchStates.Success -> {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = data.searchList.results) {
                    SearchResultCard(searchResult = it)
                }
            }
        }
    }
}

@Composable
fun SearchBar(function:(String,String)->Unit) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    TextField(
        value = searchQuery,
        onValueChange = {
            function(searchQuery,it)
            searchQuery = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Red.copy(alpha = 0.3f),
        ),
        singleLine = true,
    )
}