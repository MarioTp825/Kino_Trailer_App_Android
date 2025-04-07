package com.tepe.mymovie.ui.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tepe.domain.dashboard.MutableMovieState
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieGroup
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.ui.theme.MyMovieTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun DashboardContentView(
    mainDisplay: MutableStateFlow<MovieGroup>?,
    content: MutableMovieState,
    modifier: Modifier = Modifier,
    fetch: (MovieGroup) -> Unit = {},
    onClick: MovieCardClick,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Entertainment Center",
            style = MaterialTheme.typography
                .titleLarge
                .copy(color = MaterialTheme.colorScheme.onSurface),
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 10.dp)
        )

        val mainDisplayMovies = mainDisplay?.collectAsState()
        FeatureDisplayView(
            group = mainDisplayMovies?.value,
            modifier = Modifier.padding(vertical = 10.dp),
            onClick = onClick
        )

        content.forEach {
            val group = it.collectAsState()
            ContentGroup(
                movieGroup = group.value,
                onClick = onClick,
                fetch = fetch
            )
        }
    }
}

@Composable
private fun ContentGroup(
    movieGroup: MovieGroup,
    onClick: MovieCardClick,
    fetch: (MovieGroup) -> Unit,
) {
    if (movieGroup.error == null) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movieGroup.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .padding(start = 10.dp)
                        .background(MaterialTheme.colorScheme.onSurface)
                        .shadow(elevation = 2.dp)
                )
            }

            if (movieGroup.isLoading) {
                LoadingGroup()
            } else {
                GroupLoaded(
                    movieGroup = movieGroup,
                    onClick = onClick,
                    fetch = fetch
                )
            }
        }
    }
}

@Composable
private fun LoadingGroup() {
    Row(
        modifier = Modifier.padding(top = 10.dp),
    ) {
        (1..6).forEach { _ ->
            SkeletonMovieCard()
        }
    }
}

@Composable
private fun GroupLoaded(
    movieGroup: MovieGroup,
    onClick: MovieCardClick,
    fetch: (MovieGroup) -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(top = 10.dp),
    ) {
        items(items = movieGroup.movies) { movie ->
            MovieCard(movie, onClick)
        }

        if (movieGroup.hasMoreContent) {
            item {
                SkeletonMovieCard()
                fetch(movieGroup)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DashboardScreenPreview() {
    MyMovieTheme {
        DashboardContentView(
            mainDisplay = MutableStateFlow(MovieGroup(
                title = "Featured Movies",
                movieGenre = MovieGenre.Action,
                movies = (1..5).map {
                    MovieUI(
                        id = it.toString(),
                        title = "Movie $it",
                        imageUrl = "https://picsum.photos/200/300",
                        videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                        category = MovieGenre.TVMovie
                    )
                }
            )),
            content = (1..3).map { group ->
                MutableStateFlow(
                    MovieGroup(
                        title = "Category $group",
                        movieGenre = MovieGenre.Action,
                        movies = (1..5).map { movie ->
                            MovieUI(
                                id = movie.toString(),
                                title = "Movie $movie",
                                imageUrl = "https://picsum.photos/200/300",
                                videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                                category = MovieGenre.War
                            )
                        }
                    )
                )
            }
        ) {}
    }
}