package com.good.movies.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import com.good.movies.ui.components.ErrorComponent
import com.good.movies.ui.components.LoadingComponent


@Composable
fun SearchResultsList(
    loadState: LoadState,
    itemCount: Int,
    onRetry: () -> Unit,
    emptyMessage: String,
    content: @Composable () -> Unit
) {
    when (loadState) {
        is LoadState.Loading -> {
            LoadingComponent()
        }

        is LoadState.Error -> {
            ErrorComponent(
                message = loadState.error.message ?: "Unknown error",
                onRetry = onRetry
            )
        }

        is LoadState.NotLoading -> {
            if (itemCount == 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = emptyMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                content()
            }
        }
    }
}