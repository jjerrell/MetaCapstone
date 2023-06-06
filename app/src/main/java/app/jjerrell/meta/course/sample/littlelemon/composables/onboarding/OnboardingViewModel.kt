package app.jjerrell.meta.course.sample.littlelemon.composables.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {

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

    fun register() {
        OnboardingField.values().forEach { it.updateValidation() }
        state = if (invalidFields.isEmpty()) {
            state.copy(
                isRegistered = true
            )
        } else {
            state.copy(
                registrationFailed = true
            )
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