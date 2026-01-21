package com.good.movies.data.repository

import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : MovieRepository
