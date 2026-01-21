package com.good.movies.ui.movies.topmovieslist

sealed interface MoviesIntent {
    data object LoadTopRatedMovies : MoviesIntent
}
