package com.good.movies.ui.movies.topmovieslist


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel, modifier: Modifier = Modifier
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val topMovies = uiState.value.topRatedMovies.collectAsLazyPagingItems()

}
