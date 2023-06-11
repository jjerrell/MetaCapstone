package app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.meta.course.sample.littlelemon.domain.LittleLemonUseCase
import kotlinx.coroutines.launch

class CheckOnboardingViewModel : ViewModel() {
    var isLoading: Boolean by mutableStateOf(true)
        private set
    var isRegistered: Boolean by mutableStateOf(false)
        private set

    /**
     * Checks for previous user registration and sets the [isRegistered] value accordingly
     */
    fun checkRegistration(context: Context) {
        viewModelScope.launch {
            LittleLemonUseCase.getUseCase(context).registration.collect {
                isRegistered = it != null
                isLoading = false
            }
        }
    }
}