package app.jjerrell.meta.course.sample.littlelemon.network

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import app.jjerrell.meta.course.sample.littlelemon.network.model.UserRegistration
import app.jjerrell.meta.course.sample.littlelemon.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val KEY_FIRST_NAME = stringPreferencesKey("firstName")
private val KEY_LAST_NAME = stringPreferencesKey("lastName")
private val KEY_EMAIL = stringPreferencesKey("email")

interface IUserDataService {
    val userData: Flow<UserRegistration?>
    suspend fun updateRegistrationData(user: UserRegistration)
    suspend fun logout(onComplete: () -> Unit)
}

fun provideUserDataSource(context: Context): IUserDataService = UserDataSource(context)

private class UserDataSource(val context: Context) : IUserDataService {
    override val userData: Flow<UserRegistration?> = context.dataStore.data.map {
        val firstName = it[KEY_FIRST_NAME]
        val lastName = it[KEY_LAST_NAME]
        val email = it[KEY_EMAIL]

        if (listOfNotNull(firstName, lastName, email).count() == 3) {
            UserRegistration(
                firstName = firstName!!,
                lastName = lastName!!,
                email = email!!
            )
        } else {
            null
        }
    }

    override suspend fun updateRegistrationData(user: UserRegistration) {
        context.dataStore.updateData {
            it.toMutablePreferences().apply {
                set(KEY_FIRST_NAME, user.firstName)
                set(KEY_LAST_NAME, user.lastName)
                set(KEY_EMAIL, user.email)
            }
        }
    }

    override suspend fun logout(onComplete: () -> Unit) {
        context.dataStore.edit {
            it.clear()
            onComplete()
        }
    }
}
