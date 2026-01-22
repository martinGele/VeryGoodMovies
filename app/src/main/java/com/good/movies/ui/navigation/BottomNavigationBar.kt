package com.good.movies.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomNavigationBar(selectedRoute: String, onItemSelected: (String) -> Unit) {
    val items = listOf(
        NavigationBarItemData(
            route = NavigationItem.Home.route,
            label = NavigationItem.Home.label,
            icon = Icons.Default.Home
        ),
        NavigationBarItemData(
            route = NavigationItem.Search.route,
            label = NavigationItem.Search.label,
            icon = Icons.Default.Search
        )
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route,
                onClick = { onItemSelected(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = null
            )
        }
    }
}

private data class NavigationBarItemData(
    val route: String,
    val label: String,
    val icon: ImageVector
)
