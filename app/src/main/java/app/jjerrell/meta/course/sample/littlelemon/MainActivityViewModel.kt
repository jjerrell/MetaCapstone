package app.jjerrell.meta.course.sample.littlelemon

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jjerrell.meta.course.sample.littlelemon.domain.LittleLemonUseCase
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private lateinit var useCase: LittleLemonUseCase

    fun getAndUpdateMenu(context: Context) {
        if (!this::useCase.isInitialized) {
            useCase = LittleLemonUseCase.getUseCase(context)
        }
        viewModelScope.launch {
            useCase.getMenuItems(force = true)
        }
    }
}
