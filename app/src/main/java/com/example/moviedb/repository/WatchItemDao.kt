package com.example.moviedb.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviedb.models.WatchItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(watchItem:WatchItem)

    @Query("DELETE FROM WatchList WHERE itemId = :id")
    suspend fun removeItem(id:Int)

    @Query("SELECT * FROM WatchList")
    fun getAllWatchItems(): Flow<List<WatchItem>>
}