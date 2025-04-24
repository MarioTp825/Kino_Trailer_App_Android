package com.tepe.mymovie.ui.screens.favorites.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.ui.components.NetworkContentScreen
import com.tepe.mymovie.ui.screens.dashboard.view.MovieCardClick
import com.tepe.mymovie.ui.screens.favorites.viewModel.FavoriteMovieViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteMovieViewModel = hiltViewModel(),
    onNavigateToMovieInfo: MovieCardClick,
) {
    val movies = viewModel.favoriteMovies.collectAsState()

    NetworkContentScreen(viewModel.favoriteState) {
        FavoriteView(
            movies = movies.value,
            onClick = onNavigateToMovieInfo,
        )
    }

}

@Composable
fun FavoriteView(movies: List<MovieUI>, onClick: MovieCardClick) {
    Column {
        Text(
            "Favorite Movie Trailers",
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = movies) { movie ->
                FavoriteCard(
                    movie = movie,
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 10.dp)
                        .clickable { onClick(movie) },
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FavoriteHeaderPreview() {
    FavoriteView(
        movies = (1..5).map {
            MovieUI(
                id = it.toString(),
                title = "Harry Potter and the Prisoner of Azkaban $it",
                videoUrl = "www.darlena - jakubowski.io",
                imageUrl = "http://lorempixel.com/g/1024/768/city/",
                youtubeId = null,
                category = MovieGenre.Main,
            )
        }
    ) { }
}