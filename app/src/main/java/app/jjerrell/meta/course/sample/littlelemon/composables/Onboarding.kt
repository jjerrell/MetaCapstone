package app.jjerrell.meta.course.sample.littlelemon.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

class OnboardingViewModel : ViewModel() {
    private var state: OnboardingState by mutableStateOf(OnboardingState())

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

@Composable
fun Onboarding(
    modifier: Modifier = Modifier
) {
    val viewModel: OnboardingViewModel = viewModel()
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(260.dp)
                .padding(top = 20.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo_description),
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.26F)
                .padding(vertical = 20.dp)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tell us a little about yourself",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextField(
                value = viewModel.firstName,
                onValueChange = { viewModel.update(OnboardingField.FIRST_NAME, it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("First name")
                }
            )
            TextField(
                value = viewModel.lastName,
                onValueChange = { viewModel.update(OnboardingField.LAST_NAME, it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Last name")
                }
            )
            TextField(
                value = viewModel.emailAddress,
                onValueChange = { viewModel.update(OnboardingField.EMAIL_ADDRESS, it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Email address")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email
                )
            )
        }
        Button(
            onClick = { viewModel.register() },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            content = {
                Text("Register")
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun Onboarding_Preview() {
    LittleLemonTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        Onboarding()
    }
}