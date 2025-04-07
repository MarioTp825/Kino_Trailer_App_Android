package com.tepe.mymovie.ui.dashboard.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.R
import com.tepe.mymovie.ui.modifiers.skeleton

private val MovieCardHeight = 200.dp
private val MovieCardWidth = 160.dp
typealias MovieCardClick = (MovieUI) -> Unit

@Composable
fun MovieCard(item: MovieUI, onClick: MovieCardClick) {
    Column(
        modifier = Modifier.padding(all = 10.dp).width(MovieCardWidth),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imagePlaceholder = painterResource(R.drawable.movie_placeholder)

        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            error = imagePlaceholder,
            placeholder = imagePlaceholder,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .width(MovieCardWidth)
                .height(MovieCardHeight)
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(CornerSize(size = 10.dp)))
                .clip(RoundedCornerShape(CornerSize(size = 10.dp)))
                .clickable { onClick(item) },
        )

        Box(modifier = Modifier.height(5.dp))

        Text(
            item.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
fun SkeletonMovieCard() {
    val imagePlaceholder = painterResource(R.drawable.movie_placeholder)
    Column(
        modifier = Modifier.padding(all = 10.dp).width(MovieCardWidth),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = imagePlaceholder,
            contentDescription = "Loading poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(MovieCardWidth)
                .height(MovieCardHeight)
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(CornerSize(size = 10.dp)))
                .clip(RoundedCornerShape(CornerSize(size = 10.dp)))
                .skeleton(),
        )

        Box(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .height(20.dp)
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .skeleton()
        )
        Box(modifier = Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .height(20.dp)
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .skeleton()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MovieCardPreview() {
    Box(
        modifier = Modifier.padding(all = 10.dp).background(MaterialTheme.colorScheme.background)
    ) {
        MovieCard(
            item = MovieUI(
                id = "1",
                title = "Fake Movie with long",
                imageUrl = "https://img.youtube.com/vi/mt02_AjhaRM/maxresdefault.jpg",
                videoUrl = "https://www.youtube.com/watch?v=mt02_AjhaRM",
                category = MovieGenre.TVMovie
            )
        ) {}
    }
}