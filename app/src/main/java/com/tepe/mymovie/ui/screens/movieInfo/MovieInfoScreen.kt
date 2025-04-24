package com.tepe.mymovie.ui.screens.movieInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.tepe.cross_platform_integration.model.OnFinishCrossPlatformScreen
import com.tepe.cross_platform_integration.ui.composables.CrossPlatformComposable
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.config.di.entryPoints.ScreenRouteEntryPoint
import com.tepe.mymovie.ui.components.MovieCardDisplay
import dagger.hilt.android.EntryPointAccessors

@Composable
fun MovieInfoScreen(movieUI: MovieUI, navigateBack: OnFinishCrossPlatformScreen) {
    Column {
        MovieInfoHeader(movieUI)
        MovieInfoBody(movieUI, navigateBack)
    }
}

@Composable
private fun MovieInfoHeader(movieUI: MovieUI) {
    val youtubeId = movieUI.youtubeId

    Column {
        Text(
            text = "Trailer",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
        )
        if (youtubeId != null) {
            YoutubeScreen(youtubeId)
        } else {
            MovieCardDisplay(
                movie = movieUI,
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 20.dp)

            )
        }
    }

}

@Composable
fun YoutubeScreen(
    videoId: String,
) {
    AndroidView(factory = {
        YouTubePlayerView(it).apply {

            addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.cueVideo(
                            videoId = videoId,
                            //Skip KinoCheck intro
                            startSeconds = 3f,
                        )
                    }
                }
            )
        }
    })
}

@Composable
fun MovieInfoBody(movieUI: MovieUI, navigateBack: OnFinishCrossPlatformScreen) {
    val context = LocalContext.current
    val router = remember {
        EntryPointAccessors
            .fromApplication(
                context,
                ScreenRouteEntryPoint::class.java
            )
            .getMovieInfoScreen()
    }

    CrossPlatformComposable(
        route = router,
        parameter = movieUI,
        navigateBack = navigateBack
    )
}

@Composable
@Preview(showBackground = true)
fun MovieInfoScreenPreview() {
    val movieUI = MovieUI(
        id = "1",
        title = "Movie Title",
        imageUrl = "https://example.com/image.jpg",
        videoUrl = "www.victor - doyle.com",
        category = MovieGenre.Main,
    )
    MovieInfoHeader(movieUI)
}