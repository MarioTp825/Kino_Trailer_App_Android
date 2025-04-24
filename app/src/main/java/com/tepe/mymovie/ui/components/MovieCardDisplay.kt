package com.tepe.mymovie.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.R
import com.tepe.mymovie.ui.screens.dashboard.view.MovieCardClick

@Composable
fun MovieCardDisplay(
    movie: MovieUI,
    onClick: MovieCardClick? = null,
    modifier: Modifier = Modifier
) {
    val imagePlaceholder = painterResource(R.drawable.movie_placeholder)
    AsyncImage(
        model = movie.imageUrl,
        contentDescription = movie.title,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        error = imagePlaceholder,
        placeholder = imagePlaceholder,
        modifier = Modifier
            .then(modifier)
            .shadow(10.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick(movie) }
                } else {
                    Modifier
                }
            )
    )
}