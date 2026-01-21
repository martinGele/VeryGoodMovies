package com.good.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.good.movies.ui.movies.MoviesScreen
import com.good.movies.ui.movies.MoviesViewModel
import com.good.movies.ui.theme.VeryGoodMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VeryGoodMoviesTheme {
                val viewModel: MoviesViewModel = hiltViewModel()
                MoviesScreen(viewModel = viewModel)
            }
        }
    }
}