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

    suspend fun refreshMenuItems(force: Boolean): List<MenuItemEntity> {
        // fetch the network menu or fallback to an empty one for safety
        val menuItems: List<MenuItemEntity> = try {
            menuService.getMenu()
        } catch (e: Throwable) {
            LittleLemonMenuNetwork(menuItems = emptyList())
        }.convertToEntity()

        // while we do want to allow a force refresh, we should give it the best possibility
        // of not ending up with an empty response from this method.
        if (force && menuItems.isNotEmpty()) {
            applicationDatabase
                .menuItemDao()
                .deleteAll()
            applicationDatabase
                .menuItemDao()
                .insertAll(*menuItems.toTypedArray())
        } else {
            // get the currently stored menu
            val dbMenuItems = applicationDatabase.menuItemDao().getAll()
            // filter out any items which already exist in the database
            menuItems
                .filter { networkEntity ->
                    // make sure we don't try to import one with the same unique id
                    dbMenuItems.none { networkEntity.uid == it.uid}
                } // make sure we have something to add
                .takeIf { it.isNotEmpty() }
                ?.let {
                    // finally, add the new items
                    applicationDatabase
                        .menuItemDao()
                        .insertAll(*it.toTypedArray())
                }
        }
        // query the database again for the new list. Possibly empty, but we tried
        return applicationDatabase.menuItemDao().getAll()
    }

    suspend fun getMenuItems(): List<MenuItemEntity> =
        applicationDatabase.menuItemDao().getAll() // check the database first
            .takeIf { it.isNotEmpty() } // if items are found, return them!
            ?: refreshMenuItems(force = true) // the table seems to be empty, attempt to fetch http data

    suspend fun register(user: UserRegistration) =
        userService.updateRegistrationData(user)

    /**
     * Logs the user out and invokes the callback when finished
     *
     * @param onLogoutSuccess the action to perform after the data has been cleared
     */
    suspend fun logout(onLogoutSuccess: () -> Unit) =
        userService.logout(onComplete = onLogoutSuccess)
}

/**
 * Convert the network object to a list of database entities
 */
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