package app.jjerrell.meta.course.sample.littlelemon.domain

import app.jjerrell.meta.course.sample.littlelemon.domain.database.AppDatabase
import app.jjerrell.meta.course.sample.littlelemon.domain.network.IMenuItemNetworkService
import app.jjerrell.meta.course.sample.littlelemon.domain.database.IUserDataService
import app.jjerrell.meta.course.sample.littlelemon.domain.database.model.MenuItemEntity
import app.jjerrell.meta.course.sample.littlelemon.domain.network.model.LittleLemonMenuNetwork
import app.jjerrell.meta.course.sample.littlelemon.domain.network.model.UserRegistration

/**
 * Little lemon repository.
 *
 * This class needs the following capabilities
 * - Retrieve menu items from the network
 * - Update the database with the fetched menu items
 * - Retrieve menu items from the database
 * - Check registration
 * - Handle registration
 * - Handle logout
 *
 *
 * @property applicationDatabase
 * @property menuService
 * @property userService
 * @constructor Create empty Little lemon repository
 */
class LittleLemonRepository(
    private val applicationDatabase: AppDatabase,
    private val menuService: IMenuItemNetworkService,
    private val userService: IUserDataService
) {
    val registration = userService.userData

    suspend fun refreshMenuItems(): List<MenuItemEntity> {
        val menuItems = menuService.getMenu()
            .convertToEntity()

        applicationDatabase.menuItemDao().insertAll(
            *menuItems.toTypedArray()
        )
        return menuItems
    }

    suspend fun getMenuItems(): List<MenuItemEntity> =
        applicationDatabase.menuItemDao().getAll()

    suspend fun register(user: UserRegistration) =
        userService.updateRegistrationData(user)

    suspend fun logout(onLogoutSuccess: () -> Unit) =
        userService.logout(onComplete = onLogoutSuccess)
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