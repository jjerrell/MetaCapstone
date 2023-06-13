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
import app.jjerrell.meta.course.sample.littlelemon.util.string.isProbablyValidEmail
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
        state = state.copy(isLoading = true, registrationFailed = false)
        if (invalidFields.isEmpty()) {
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
        } else {
            state = state.copy(registrationFailed = true)
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
        if (invalidFields.isEmpty()) {
            resetRegistration()
        }
    }

    private fun OnboardingField.isValid(): Boolean = when (this) {
        OnboardingField.FIRST_NAME -> state.firstName.isNotBlank()
        OnboardingField.LAST_NAME -> state.lastName.isNotBlank()
        OnboardingField.EMAIL_ADDRESS -> state.emailAddress.isProbablyValidEmail()
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