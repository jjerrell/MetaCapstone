package app.jjerrell.meta.course.sample.littlelemon.ui.composables.main

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.meta.course.sample.littlelemon.domain.LittleLemonUseCase
import app.jjerrell.meta.course.sample.littlelemon.domain.network.model.UserRegistration
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    var state: UserRegistration? by mutableStateOf(null)
        private set

    fun loadUserData(context: Context) {
        val useCase = LittleLemonUseCase.getUseCase(context)
        viewModelScope.launch {
            useCase.registration.collect {
                state = it
            }
        }
    }

    fun logout(context: Context, whenCleared: () -> Unit) {
        val useCase = LittleLemonUseCase.getUseCase(context)
        viewModelScope.launch {
            useCase.logout(whenCleared)
        }
    }
}