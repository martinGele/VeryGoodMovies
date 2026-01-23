package com.good.movies.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.good.movies.ui.theme.Spacing

@Composable
fun ContentTypeSelector(
    selectedContentType: ContentType,
    onContentTypeSelected: (ContentType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.small)
    ) {
        FilterChip(
            selected = selectedContentType == ContentType.MOVIES,
            onClick = { onContentTypeSelected(ContentType.MOVIES) },
            label = { Text("Movies") }
        )
        FilterChip(
            selected = selectedContentType == ContentType.TV_SERIES,
            onClick = { onContentTypeSelected(ContentType.TV_SERIES) },
            label = { Text("TV Series") }
        )
    }
}
