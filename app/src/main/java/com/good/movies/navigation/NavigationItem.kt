package com.good.movies.navigation


/**
 * Sealed class representing navigation items in the app.
 * and DetailsScreen where the movieId is passed as an argument.
 */
sealed class NavigationItem(val route: String, val label: String) {
    object Home : NavigationItem("home", "Home")
    object Search : NavigationItem("search", "Search")
    object Details : NavigationItem("details/{movieId}", "Details") {
        fun createRoute(movieId: Int): String = "details/$movieId"
    }

    object TvDetails : NavigationItem("tv_details/{tvId}", "TV Details") {
        fun createRoute(tvId: Int): String = "tv_details/$tvId"
    }

    object Favorites : NavigationItem("favorites", "Favorites")
}
