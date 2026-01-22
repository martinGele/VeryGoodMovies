package com.good.movies.ui.navigation


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
}
