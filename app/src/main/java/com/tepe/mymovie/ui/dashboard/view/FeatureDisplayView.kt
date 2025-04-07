package com.tepe.mymovie.ui.dashboard.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieGroup
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.R
import com.tepe.mymovie.ui.modifiers.skeleton


@Composable
fun FeatureDisplayView(
    modifier: Modifier = Modifier,
    group: MovieGroup?,
    onClick: MovieCardClick
) {
    if (group == null || group.isLoading) {
        MovieDisplayLoading()
    } else {
        val state = rememberPagerState(pageCount = { group.movies.size })
        HorizontalPager(state, modifier) { page ->
            MovieCardDisplay(group.movies[page], onClick)
        }
    }
}

@Composable
private fun MovieDisplayLoading() {
    val imagePlaceholder = painterResource(R.drawable.movie_placeholder)
    Card(
        modifier = Modifier
            .height(350.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .skeleton(),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Image(
            painter = imagePlaceholder,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun MovieCardDisplay(movie: MovieUI, onClick: MovieCardClick) {
    val imagePlaceholder = painterResource(R.drawable.movie_placeholder)

    Card(
        modifier = Modifier
            .height(350.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClick(movie)
            },
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        AsyncImage(
            model = movie.imageUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            error = imagePlaceholder,
            placeholder = imagePlaceholder,
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FeatureDisplayViewPreview() {
    FeatureDisplayView(
        group = MovieGroup(
            title = "Featured Movies",
            movies = (1..5).map {
                MovieUI(
                    id = it.toString(),
                    title = "Movie $it",
                    imageUrl = "https://picsum.photos/200/300",
                    videoUrl = "https://www.youtube.com/watch?v=mt02_AjhaRM",
                    category = MovieGenre.Action
                )
            },
            MovieGenre.Main
        )
    ) { }
}
