package app.jjerrell.meta.course.sample.littlelemon.ui.composables.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.meta.course.sample.littlelemon.domain.LittleLemonUseCase
import app.jjerrell.meta.course.sample.littlelemon.ui.model.MenuItemAndroid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainPageViewModel : ViewModel() {
    var stateFlow = MutableStateFlow(MainPageUiState())
        private set

    val menuItems = stateFlow.map { uiState ->
        uiState.menuItems.filter { item ->
            uiState.category?.let {
                item.category == it
            } ?: true && item.matches()
        }
    }

    fun fetchMenuItems(context: Context) {
        val useCase = LittleLemonUseCase.getUseCase(context)
        viewModelScope.launch {
            val currentState = stateFlow.value
            stateFlow.value = currentState.copy(
                isLoading = false,
                menuItems = useCase.getMenuItems()
            )
        }
    }

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
    val isLoading: Boolean = true,
    val searchContent: String = "",
    val category: MenuItemAndroid.Category? = null,
    val menuItems: List<MenuItemAndroid> = emptyList()
)