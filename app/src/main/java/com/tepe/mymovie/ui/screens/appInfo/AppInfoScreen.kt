package com.tepe.mymovie.ui.screens.appInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tepe.cross_platform_integration.ui.modifiers.launchCrossPlatformActivity
import com.tepe.mymovie.R
import com.tepe.mymovie.config.di.entryPoints.ScreenRouteEntryPoint
import dagger.hilt.android.EntryPointAccessors

@Composable
fun AppInfoScreen() {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val router = remember {
        EntryPointAccessors
            .fromApplication(
                context,
                ScreenRouteEntryPoint::class.java
            )
            .getAppInfoScreen()
    }

    AppInfoView(
        Modifier.launchCrossPlatformActivity<Any>(
            route = router
        )
    ) {
        uriHandler.openUri("https://api.kinocheck.com")
    }
}

@Composable
fun AppInfoView(createdByModifier: Modifier, onKinoCheckClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            "App Information",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .fillMaxWidth()
        )

        val image = painterResource(R.drawable.app_icon)
        Image(
            painter = image,
            contentDescription = "Movie Trailer",
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 30.dp)
                .clip(RoundedCornerShape(50.dp))
        )

        Text(
            text = stringResource(R.string.desc_movie_app),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(horizontal = 30.dp)
        )

        InfoItem(
            label = stringResource(R.string.created_by),
            value = stringResource(R.string.developer_name),
            modifier = createdByModifier
        )

        InfoItem(
            label = stringResource(R.string.resources),
            value = stringResource(R.string.api_provider),
            onClick = onKinoCheckClick,
        )
    }
}

@Composable
private fun InfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)

        if (onClick == null) {
            Text(
                value,
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .then(modifier)
            )
        } else {
            InfoButton(value, modifier, onClick)
        }
    }
}

@Composable
fun InfoButton(value: String, modifier: Modifier, onClick: () -> Unit) {
    TextButton(
        modifier = modifier,
        onClick = {
            onClick()
        }
    ) {
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AppInfoScreenPreview() {
    AppInfoView(createdByModifier = Modifier) {

    }
}