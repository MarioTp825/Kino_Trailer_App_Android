package com.tepe.mymovie.ui.screens.favorites.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tepe.domain.model.movie.MovieUI
import com.tepe.domain.model.network.NetworkState
import com.tepe.domain.repository.MovieLocalRepository
import com.tepe.domain.util.network.apiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val movieLocalRepository: MovieLocalRepository
) : ViewModel() {
    private val _favoriteState: MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.None)
    val favoriteState: StateFlow<NetworkState> get() = _favoriteState

    private val _favoriteMovies: MutableStateFlow<List<MovieUI>> = MutableStateFlow(emptyList())
    val favoriteMovies: StateFlow<List<MovieUI>> get() = _favoriteMovies

    init {
        getAllFavoriteMovies()
    }

    private fun getAllFavoriteMovies() = viewModelScope.launch {
        _favoriteState.value = NetworkState.Loading

        val movies = apiCall { movieLocalRepository.getStoredMovies() }

        if (movies.error != null) {
            _favoriteState.value = NetworkState.Error(movies.error?.message)
            return@launch
        }

        val data = movies.data ?: return@launch

        _favoriteState.value = NetworkState.Successful
        data.collect {
            _favoriteMovies.value = it
        }
    }

}