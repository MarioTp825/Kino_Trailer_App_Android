package com.tepe.mymovie.ui.screens.dashboard.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieGroup
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.R
import com.tepe.mymovie.ui.components.MovieCardDisplay
import com.tepe.mymovie.ui.modifiers.skeleton
import kotlin.math.abs


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
        HorizontalPager(
            state = state,
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 40.dp),
            pageSpacing = 20.dp,
        ) { page ->
            val pageOffset = abs(state.currentPage - page) + state.currentPageOffsetFraction

            val scale = animateFloatAsState(
                targetValue = if (pageOffset == 0f) 1.0f else 0.95f,
                label = "ScaleAnimation"
            )

            MovieCardDisplay(
                movie = group.movies[page],
                onClick = onClick.takeIf { page == state.currentPage },
                modifier = Modifier
                    .height(350.dp)
                    .graphicsLayer {
                        this.scaleX = scale.value
                        this.scaleY = scale.value
                    }
            )
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
