package com.good.movies.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.good.movies.ui.details.DetailsScreen
import com.good.movies.ui.favorites.FavoritesScreen
import com.good.movies.ui.movies.topmovieslist.MoviesListScreen
import com.good.movies.ui.search.SearchScreen

/**
 * Main navigation graph for the app, defining the navigation structure
 * including HomeScreen, SearchScreen, and DetailsScreen where the movieId is passed as an argument.
 */
@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavigationItem.Home.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = currentRoute,
                onItemSelected = { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Home.route) {
                MoviesListScreen(
                    innerPaddingValues = innerPadding,
                    onMovieClick = { movieId ->
                        navController.navigate(NavigationItem.Details.createRoute(movieId))
                    }
                )
            }
            composable(NavigationItem.Search.route) {
                SearchScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(NavigationItem.Details.createRoute(movieId))
                    }
                )
            }
            composable(NavigationItem.Favorites.route) {
                FavoritesScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(NavigationItem.Details.createRoute(movieId))
                    }
                )
            }
            composable(
                route = NavigationItem.Details.route,
                arguments = listOf(
                    navArgument("movieId") { type = NavType.IntType }
                )
            ) {
                DetailsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
