package com.example.moviedb.di

import android.content.Context
import androidx.room.Room
import com.example.moviedb.models.WatchItem
import com.example.moviedb.network.EntertainmentApi
import com.example.moviedb.repository.AppDatabase
import com.example.moviedb.repository.MainRepository
import com.example.moviedb.util.Constants.Companion.BASE_URL
import com.example.moviedb.repository.DefaultMainRepository
import com.example.moviedb.repository.WatchItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieApi(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiInstance(retrofit:Retrofit): EntertainmentApi =
        retrofit.create(EntertainmentApi::class.java)

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "WatchListDatabase"
    ).build()

    @Singleton
    @Provides
    fun provideWatchItemDao(db:AppDatabase):WatchItemDao = db.getWatchItemDao()

    @Singleton
    @Provides
    fun provideMainRepository(api:EntertainmentApi,watchItemDao:WatchItemDao): MainRepository = DefaultMainRepository(api,watchItemDao)

}