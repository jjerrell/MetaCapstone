package app.jjerrell.meta.course.sample.littlelemon.composables.main

import androidx.lifecycle.ViewModel
import app.jjerrell.meta.course.sample.littlelemon.ui.model.MenuItemAndroid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class MainPageViewModel : ViewModel() {
    var stateFlow = MutableStateFlow(MainPageUiState())
        private set

    val menuItems = stateFlow.map { uiState ->
        _menuItems.filter { item ->
            uiState.category?.let {
                item.category == it
            } ?: true && item.matches()
        }
    }

    private val _menuItems: List<MenuItemAndroid> = MenuItemAndroid.defaultMenu

    fun updateSearchContent(text: String) {
        val currentState = stateFlow.value
        stateFlow.value = currentState.copy(searchContent = text)
    }

    fun updateCategory(option: MenuItemAndroid.Category? = null) {
        val currentState = stateFlow.value
        stateFlow.value = currentState.copy(
            category = option,
            searchContent = if (option == null) "" else currentState.searchContent
        )
    }

    private fun MenuItemAndroid.matches(): Boolean = stateFlow.value.searchContent
        .takeUnless { it.isBlank() }
        ?.let { this.title.contains(it) || this.description.contains(it) }
        ?: true
}

data class MainPageUiState(
    var searchContent: String = "",
    var category: MenuItemAndroid.Category? = null
)