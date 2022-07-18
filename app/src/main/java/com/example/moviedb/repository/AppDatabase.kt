package com.example.moviedb.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.moviedb.models.WatchItem
import com.example.moviedb.util.Converter

@Database(entities = [WatchItem::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWatchItemDao(): WatchItemDao

}