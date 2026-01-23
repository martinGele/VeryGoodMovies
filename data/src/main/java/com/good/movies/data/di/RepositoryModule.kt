package com.good.movies.data.di

import com.good.movies.data.repository.FavoriteMovieRepositoryImpl
import com.good.movies.data.repository.FavoriteTvSeriesRepositoryImpl
import com.good.movies.data.repository.MovieRepositoryImpl
import com.good.movies.data.repository.TvRepositoryImpl
import com.good.movies.domain.repository.FavoriteMovieRepository
import com.good.movies.domain.repository.FavoriteTvSeriesRepository
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

    @Binds
    @Singleton
    abstract fun bindFavoriteMovieRepository(
        favoriteMovieRepositoryImpl: FavoriteMovieRepositoryImpl
    ): FavoriteMovieRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteTvSeriesRepository(
        favoriteTvSeriesRepositoryImpl: FavoriteTvSeriesRepositoryImpl
    ): FavoriteTvSeriesRepository
}
