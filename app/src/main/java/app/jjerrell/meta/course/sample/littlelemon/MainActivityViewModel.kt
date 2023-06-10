package app.jjerrell.meta.course.sample.littlelemon

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.meta.course.sample.littlelemon.database.model.MenuItemEntity
import app.jjerrell.meta.course.sample.littlelemon.network.IMenuItemNetworkService
import app.jjerrell.meta.course.sample.littlelemon.network.MenuItemNetworkService
import app.jjerrell.meta.course.sample.littlelemon.network.model.LittleLemonMenuNetwork
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val networkService: IMenuItemNetworkService = MenuItemNetworkService(ktorHttpClient)

    fun getAndUpdateMenu(context: Context) {
        viewModelScope.launch {
            val database = context.database
            val menu = networkService.getMenu()
            database.userDao().insertAll(
                *menu.convertToEntity().toTypedArray()
            )
        }
    }

}

private fun LittleLemonMenuNetwork.convertToEntity(): List<MenuItemEntity> =
    this.menuItems.map {
        MenuItemEntity(
            uid = it.id,
            title = it.title,
            description = it.description,
            price = it.price,
            image = it.image,
            category = it.category
        )
    }