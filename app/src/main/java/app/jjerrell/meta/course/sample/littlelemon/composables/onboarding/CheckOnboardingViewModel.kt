package app.jjerrell.meta.course.sample.littlelemon.composables.onboarding

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.meta.course.sample.littlelemon.KEY_EMAIL
import app.jjerrell.meta.course.sample.littlelemon.KEY_FIRST_NAME
import app.jjerrell.meta.course.sample.littlelemon.KEY_LAST_NAME
import app.jjerrell.meta.course.sample.littlelemon.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CheckOnboardingViewModel : ViewModel() {
    var isLoading: Boolean by mutableStateOf(true)
        private set
    var isRegistered: Boolean by mutableStateOf(false)
        private set

    /**
     * Checks for previous user registration and sets the [isRegistered] value accordingly
     *
     * @param context the context to use for retrieving data from the data store
     */
    fun checkRegistration(context: Context) {
        viewModelScope.launch {
            isRegistered = context.dataStore.data
                .first().let { preferences ->
                    isLoading = false
                    val firstName = preferences[KEY_FIRST_NAME]
                    val lastName = preferences[KEY_LAST_NAME]
                    val email = preferences[KEY_EMAIL]
                    !(firstName.isNullOrBlank() || lastName.isNullOrBlank() || email.isNullOrBlank())
                }
        }
    }
}