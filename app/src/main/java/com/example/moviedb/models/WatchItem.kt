package com.example.moviedb.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Watchlist")
data class WatchItem(
    @PrimaryKey(autoGenerate = true)
    var itemId:Int = 0,
    val type:Boolean,
    @Embedded(prefix = "movie_")
    val movie: Movie?,
    @Embedded(prefix = "series_")
    val series: Series?
)