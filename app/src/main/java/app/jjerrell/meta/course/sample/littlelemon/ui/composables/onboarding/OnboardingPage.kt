package app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLButton
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLHero
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLTopAppBar
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.PageLoadingIndicator
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.RegistrationFormFields
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

@Composable
@ExperimentalComposeUiApi
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
fun OnboardingPage(
    modifier: Modifier = Modifier,
    onRegistrationSuccess: () -> Unit
) {
    val viewModel: OnboardingViewModel = viewModel()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            LLTopAppBar()
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .imePadding() // allows the view to adjust itself when the keyboard is open (targetApi = tiramisu)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            item {
                LLHero(
                    modifier = Modifier
                        .heightIn(min = 120.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.registration_hero),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            item {
                if (viewModel.state.registrationFailed) {
                    Text(
                        text = stringResource(R.string.form_input_invalid),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                RegistrationFormFields(
                    modifier = Modifier
                        .fillMaxHeight(),
                    isEnabled = !viewModel.state.isLoading,
                    firstName = viewModel.state.firstName,
                    lastName = viewModel.state.lastName,
                    email = viewModel.state.emailAddress,
                    invalidFields = viewModel.invalidFields.toList(),
                    updateFirstName = { viewModel.update(OnboardingField.FIRST_NAME, it) },
                    updateLastName = { viewModel.update(OnboardingField.LAST_NAME, it) },
                    updateEmail = { viewModel.update(OnboardingField.EMAIL_ADDRESS, it) },
                    whenDone = {
                        focusManager.clearFocus()
                        viewModel.register(context = context)
                    }
                )
                if (viewModel.state.isLoading) {
                    PageLoadingIndicator()
                }
            }
            item {
                LLButton(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.register(context = context)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 20.dp),
                    enabled = !viewModel.state.isLoading,
                    title = stringResource(R.string.button_register)
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
    /**
     * Waits for the isRegistered state value to be set to true and then calls the onSuccess callback.
     */
    when (viewModel.state.isRegistered) {
        true -> LaunchedEffect(Unit) {
            onRegistrationSuccess()
        }
        else -> {}
    }
}

@Preview(showSystemUi = true)
@Composable
@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
private fun Onboarding_Preview() {
    LittleLemonTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        OnboardingPage(
            onRegistrationSuccess = { }
        )
    }
}