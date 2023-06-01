package app.jjerrell.meta.course.sample.littlelemon.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

class OnboardingViewModel : ViewModel() {
    var state: OnboardingState by mutableStateOf(OnboardingState())
        private set

    var firstName: String by mutableStateOf(state.firstName)
        private set
    var lastName: String by mutableStateOf(state.lastName)
        private set
    var emailAddress: String by mutableStateOf(state.emailAddress)
        private set

    fun update(field: OnboardingField, value: String) {
        when (field) {
            OnboardingField.FIRST_NAME -> firstName = value
            OnboardingField.LAST_NAME -> lastName = value
            OnboardingField.EMAIL_ADDRESS -> emailAddress = value
        }
    }

    fun register() {
        state = state.copy(
            isRegistered = true,
            firstName = firstName,
            lastName = lastName,
            emailAddress = emailAddress
        )
    }
}

enum class OnboardingField {
    FIRST_NAME,
    LAST_NAME,
    EMAIL_ADDRESS
}

data class OnboardingState(
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val emailAddress: String = ""
)

//sealed interface OnboardingUiState {
//    object Initialize : OnboardingUiState
//    object Loading : OnboardingUiState
//    data class Ready(val result: String)
//    data class Error(val error: Throwable)
//}

@Composable
fun Onboarding(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo_description)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
//            TextField(value = , onValueChange = )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Onboarding_Preview() {
    LittleLemonTheme {
        Onboarding()
    }
}