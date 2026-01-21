package com.good.movies.ui.movies.topmovieslist

sealed interface MoviesIntent {
    data object LoadTopRatedMovies : MoviesIntent
    data class Search(val query: String) : MoviesIntent
    data object ClearSearch : MoviesIntent
}
