package com.tepe.domain.model.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaginatedHolder <T>(
    initPage: Int = 0,
) {
    private val _items: MutableStateFlow<List<T>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<T>> = _items

    private var lastPage: Int = Int.MAX_VALUE
    var currentPage: Int = initPage
        private set

    val isAtLastPage: Boolean
        get() = currentPage >= lastPage


    fun fetchItems(newItems: Collection<T>, lastPage: Int) {
        _items.value += newItems
        this.lastPage = lastPage

        if(currentPage < lastPage) {
            currentPage++
        }
    }


}