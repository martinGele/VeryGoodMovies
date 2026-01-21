package com.good.movies.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing {
    val none: Dp = 0.dp
    val extraSmall: Dp = 4.dp
    val small: Dp = 8.dp
    val mediumSmall: Dp = 12.dp
    val medium: Dp = 16.dp
    val mediumLarge: Dp = 24.dp
    val large: Dp = 32.dp
    val extraLarge: Dp = 48.dp
    val huge: Dp = 64.dp
}

object Size {
    val iconSmall: Dp = 16.dp
    val iconMedium: Dp = 24.dp
    val iconLarge: Dp = 32.dp
    val iconExtraLarge: Dp = 48.dp
    val buttonHeight: Dp = 48.dp
    val cardElevation: Dp = 4.dp
    val borderWidth: Dp = 1.dp
    val dividerThickness: Dp = 1.dp
}

object Radius {
    val none: Dp = 0.dp
    val small: Dp = 4.dp
    val medium: Dp = 8.dp
    val large: Dp = 12.dp
    val extraLarge: Dp = 16.dp
    val round: Dp = 50.dp
}

data class Dimens(
    val spacing: Spacing = Spacing,
    val size: Size = Size,
    val radius: Radius = Radius
)

val LocalDimens = staticCompositionLocalOf { Dimens() }
