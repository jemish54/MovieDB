package com.example.moviedb.di

import com.example.moviedb.network.EntertainmentApi
import com.example.moviedb.repository.MainRepository
import com.example.moviedb.util.Constants.Companion.BASE_URL
import com.example.moviedb.repository.DefaultMainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideMainRepository(api:EntertainmentApi): MainRepository = DefaultMainRepository(api)

}