package com.example.moviedb.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Watchlist")
data class WatchItem(
    @PrimaryKey()
    val itemId:Int,
    val type:Boolean,
    @Embedded(prefix = "movie_")
    val movie: Movie? = null,
    @Embedded(prefix = "series_")
    val series: Series? = null
)