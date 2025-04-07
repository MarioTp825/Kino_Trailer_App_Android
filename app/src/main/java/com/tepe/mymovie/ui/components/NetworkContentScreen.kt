package com.tepe.mymovie.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tepe.domain.model.network.NetworkState
import com.tepe.domain.model.network.NetworkState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun NetworkContentScreen(
    networkState: StateFlow<NetworkState>,
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val response = networkState.collectAsState()
    when(val state = response.value) {
        is None, is Loading -> {
            Loading()
        }
        is Error -> Error(
            message = state.message,
            modifier = Modifier.fillMaxSize()
        )
        is Successful, is Paginating-> {
            content()
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("Loading...", style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun Error(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Warning, contentDescription = "Error")
        Text("Error", style = MaterialTheme.typography.titleMedium)
        Text(message, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true, name = "error")
@Composable
fun ErrorPreview() {
    NetworkContentScreen(MutableStateFlow(NetworkState.Error("Unknown Error"))) { }
}

@Preview(showBackground = true, name = "loading")
@Composable
fun LoadingPreview() {
    NetworkContentScreen(MutableStateFlow(Loading)) { }
}

@Preview(showBackground = true, name = "successful")
@Composable
fun SuccessfulPreview() {
    NetworkContentScreen(
        networkState = MutableStateFlow<NetworkState>(Successful) ,
    ) {
        Text("Successful")
    }
}