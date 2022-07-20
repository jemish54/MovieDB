package com.example.moviedb.models

data class SearchResponse(
    val page: Int,
    var results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)