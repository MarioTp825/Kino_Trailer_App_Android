package com.tepe.mymovie.ui.dashboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tepe.data.network.getNetworkMessageError
import com.tepe.domain.model.movie.MovieGroup
import com.tepe.domain.dashboard.DashboardPageManager
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.network.NetworkState
import com.tepe.domain.repository.DashboardRepository
import com.tepe.domain.util.network.apiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository,
    val dashboardPageManager: DashboardPageManager
) : ViewModel() {
    private val _dashboardState: MutableStateFlow<NetworkState> =
        MutableStateFlow(NetworkState.None)
    val dashboardState: StateFlow<NetworkState> get() = _dashboardState

    init {
        getSchema()
    }

    private fun getSchema() = viewModelScope.launch {
        if (dashboardState.value.isLoading()) return@launch

        _dashboardState.value = NetworkState.Loading

        val response = apiCall {
            repository.getSchema()
        }

        val data = response.data
        if (data == null) {
            val message = getNetworkMessageError(response.forceError)
            _dashboardState.value = NetworkState.Error(message = message)
            return@launch
        }

        dashboardPageManager.setDashboardSchema(data)
        _dashboardState.value = NetworkState.Successful

        withContext(Dispatchers.Default) {
            dashboardPageManager.schemaGenre.map {
                async { fetchTrailersPage(it) }
            }
        }
        dashboardPageManager
    }

    private suspend fun fetchTrailersPage(category: MovieGenre) {
        val page = dashboardPageManager[category] ?: return
        if (!page.value.hasMoreContent) return

        val response = apiCall {
            repository.getTrailers(category = category, page = page.value.currentPage)
        }

        val data = response.data

        if(data == null) {
            withContext(Dispatchers.Main) {
                dashboardPageManager.setError(page.value.movieGenre, response.error?.message)
            }
            return
        }

        val group = MovieGroup(
            title = page.value.title,
            movies = data.result,
            movieGenre = category,
            currentPage = page.value.currentPage + 1
        )
        withContext(Dispatchers.Main) {
            dashboardPageManager.fetchTrailersPage(group)
        }
    }

    fun fetchTrailersPage(movieGroup: MovieGroup) = viewModelScope.launch{
        fetchTrailersPage(movieGroup.movieGenre)
    }
}