package com.example.moviedb.models

import androidx.room.Entity

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val known_for: List<KnownFor>,
    val media_type: String,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val profile_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun ResultToMovie(result:Result):Movie =
    Movie(
        adult = result.adult,
        backdrop_path = result.backdrop_path,
        genre_ids = result.genre_ids,
        id = result.id,
        original_language = result.original_language,
        original_title = result.original_title,
        overview = result.overview,
        popularity = result.popularity,
        poster_path = result.poster_path,
        release_date = result.release_date,
        title = result.title,
        video = result.video,
        vote_average = result.vote_average,
        vote_count = result.vote_count
    )

fun ResultToSeries(result:Result):Series =
    Series(
        backdrop_path = result.backdrop_path,
        first_air_date = result.first_air_date,
        genre_ids = result.genre_ids,
        id = result.id,
        name = result.name,
        origin_country = result.origin_country,
        original_language = result.original_language,
        original_name = result.original_name,
        overview = result.overview,
        popularity = result.popularity,
        poster_path = result.poster_path,
        vote_average = result.vote_average,
        vote_count = result.vote_count
    )