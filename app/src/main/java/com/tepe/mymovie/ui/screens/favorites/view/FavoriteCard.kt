package com.tepe.mymovie.ui.screens.favorites.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.R

@Composable
fun FavoriteCard(movie: MovieUI, modifier: Modifier = Modifier) {
    val imagePlaceholder = painterResource(R.drawable.movie_placeholder)
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            AsyncImage(
                model = movie.imageUrl,
                contentDescription = movie.title,
                error = imagePlaceholder,
                placeholder = imagePlaceholder,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .shadow(elevation = 2.dp, shape = CircleShape)
                    .clip(shape = CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(start = 20.dp, end = 5.dp)
            ) {
                Text(
                    text = movie.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = movie.category.name,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FavoriteCardPreview() {
    FavoriteCard(
        movie = MovieUI(
            id = "1",
            title = "Harry Potter and the Prisoner of Azkaban",
            videoUrl = "www.darlena - jakubowski.io",
            imageUrl = "http://lorempixel.com/g/1024/768/city/",
            youtubeId = null,
            category = MovieGenre.Main,
        )
    )
}