package app.jjerrell.meta.course.sample.littlelemon.domain

import android.content.Context
import app.jjerrell.meta.course.sample.littlelemon.database
import app.jjerrell.meta.course.sample.littlelemon.domain.database.model.MenuItemEntity
import app.jjerrell.meta.course.sample.littlelemon.domain.database.provideUserDataSource
import app.jjerrell.meta.course.sample.littlelemon.domain.network.MenuItemNetworkService
import app.jjerrell.meta.course.sample.littlelemon.domain.network.model.UserRegistration
import app.jjerrell.meta.course.sample.littlelemon.ktorHttpClient
import app.jjerrell.meta.course.sample.littlelemon.ui.model.MenuItemAndroid

class LittleLemonUseCase private constructor(context: Context) {
    private val repository = LittleLemonRepository(
        applicationDatabase = context.database,
        menuService = MenuItemNetworkService(ktorHttpClient),
        userService = provideUserDataSource(context)
    )

    val registration = repository.registration

    suspend fun getMenuItems(force: Boolean = false): List<MenuItemAndroid> {
        val dbResponse = repository.getMenuItems()
        return if (dbResponse.isEmpty() || force) {
            repository.refreshMenuItems()
        } else {
            dbResponse
        }.map { it.convertToAndroid() }
    }

    suspend fun registerUser(
        user: UserRegistration
    ) = repository.register(user)

    suspend fun logout(invokeOnCompletion: () -> Unit) =
        repository.logout(invokeOnCompletion)

    companion object {
        private var INSTANCE: LittleLemonUseCase? = null

        fun getUseCase(context: Context): LittleLemonUseCase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = LittleLemonUseCase(context)
                }
            }
            return INSTANCE!!
        }
    }
}

private fun MenuItemEntity.convertToAndroid() = MenuItemAndroid(
    title = this.title,
    description = this.description,
    category = MenuItemAndroid.Category.fromServiceName(this.category),
    imageUri = this.image,
    price = this.price.toDoubleOrNull() ?: 8.99
)