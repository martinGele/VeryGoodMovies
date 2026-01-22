package com.good.movies.ui.navigation

sealed class NavigationItem(val route: String, val label: String) {
    object Home : NavigationItem("home", "Home")
    object Search : NavigationItem("search", "Search")
}
