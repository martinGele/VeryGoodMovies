package com.good.movies.data.di

import com.good.movies.data.repository.MovieRepositoryImpl
import com.good.movies.data.repository.TvRepositoryImpl
import com.good.movies.domain.repository.MovieRepository
import com.good.movies.domain.repository.TvRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to bind repository implementations to their interfaces.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindTvRepository(
        tvRepositoryImpl: TvRepositoryImpl
    ): TvRepository
}
