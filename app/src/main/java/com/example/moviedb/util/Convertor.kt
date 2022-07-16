package com.example.moviedb.util

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromGenre(genre:List<Int>) : Int{
        return genre[0]
    }
    @TypeConverter
    fun  toGenre(genre:Int): List<Int> {
        return mutableListOf(genre)
    }

    @TypeConverter
    fun fromCountry(countries:List<String>) : String{
        return countries[0]
    }
    @TypeConverter
    fun  toCountry(country:String): List<String> {
        return mutableListOf(country)
    }
}