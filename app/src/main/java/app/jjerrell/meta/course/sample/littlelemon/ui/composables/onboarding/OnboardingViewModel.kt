package app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.meta.course.sample.littlelemon.domain.LittleLemonUseCase
import app.jjerrell.meta.course.sample.littlelemon.domain.network.model.UserRegistration
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {
    private lateinit var useCase: LittleLemonUseCase

    var state: OnboardingState by mutableStateOf(OnboardingState())
        private set

    val invalidFields = mutableStateListOf<OnboardingField>()

    fun update(field: OnboardingField, value: String) {
        state = when (field) {
            OnboardingField.FIRST_NAME -> state.copy(firstName = value)
            OnboardingField.LAST_NAME -> state.copy(lastName = value)
            OnboardingField.EMAIL_ADDRESS -> state.copy(emailAddress = value)
        }
        field.updateValidation()
    }

    fun register(context: Context) {
        if (!this::useCase.isInitialized) {
            useCase = LittleLemonUseCase.getUseCase(context)
        }
        OnboardingField.values().forEach { it.updateValidation() }
        viewModelScope.launch {
            useCase.registerUser(
                user = UserRegistration(
                    firstName = state.firstName,
                    lastName = state.lastName,
                    email = state.emailAddress
                )
            )
            useCase.registration.collect {
                state = if (it == null) state.copy(
                    registrationFailed = true
                ) else state.copy(
                    isRegistered = true
                )
            }
        }
    }

    fun resetRegistration() {
        state = state.copy(registrationFailed = false)
    }

    private fun OnboardingField.updateValidation() {
        if (this.isValid()) {
            invalidFields.remove(this)
        } else if (!invalidFields.contains(this)) {
            invalidFields.add(this)
        }
    }

    private fun OnboardingField.isValid(): Boolean = when (this) {
        OnboardingField.FIRST_NAME -> state.firstName.isNotBlank()
        OnboardingField.LAST_NAME -> state.lastName.isNotBlank()
        OnboardingField.EMAIL_ADDRESS -> emailIsValid()
    }

    private fun emailIsValid(): Boolean = if (state.emailAddress.isBlank()) {
        false
    } else {
        val parts = state.emailAddress.split('@')
        println("_DEBUG: $parts")
        val result = if (parts.count() != 2)
            false
        else {
            val domainParts = parts[1].split('.')
            domainParts.count() >= 2 && domainParts[1].isNotBlank()
        }
        println("_DEBUG isValidEmail: $result")
        result
    }
}

enum class OnboardingField {
    FIRST_NAME,
    LAST_NAME,
    EMAIL_ADDRESS
}

data class OnboardingState(
    val isLoading: Boolean = false,
    val hasLoaded: Boolean = false,
    val isRegistered: Boolean = false,
    val registrationFailed: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val emailAddress: String = ""
)