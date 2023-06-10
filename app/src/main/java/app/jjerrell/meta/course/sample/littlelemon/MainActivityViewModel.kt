package app.jjerrell.meta.course.sample.littlelemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.meta.course.sample.littlelemon.data.IMenuItemNetworkService
import app.jjerrell.meta.course.sample.littlelemon.data.MenuItemNetworkService
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val networkService: IMenuItemNetworkService = MenuItemNetworkService(ktorHttpClient)

    fun getAndUpdateMenu() {
        viewModelScope.launch {
            val menu = networkService.getMenu()
            // TODO: convert and write to db
        }
    }

}