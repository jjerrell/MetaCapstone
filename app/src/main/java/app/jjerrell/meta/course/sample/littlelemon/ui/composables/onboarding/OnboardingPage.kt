package app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLHero
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLTopAppBar
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
        Column(
            modifier = modifier
                .padding(paddingValues)
                .imePadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LLHero(
                modifier = Modifier
                    .fillMaxHeight(0.25F),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.registration_hero),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            when (viewModel.state.registrationFailed) {
                true -> Text(stringResource(R.string.form_input_invalid))
                else -> {}
            }
            RegistrationFormFields(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
                isLoading = viewModel.state.isLoading,
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
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.register(context = context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 20.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                enabled = !viewModel.state.isLoading,
                content = {
                    Text(stringResource(R.string.button_register))
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
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

/**
 * Collection of form fields used to register the user for the application
 *
 * Uses the [state hoisting](https://developer.android.com/jetpack/compose/state#state-hoisting)
 * pattern to perform read/write activities.
 */
@Composable
@ExperimentalComposeUiApi
private fun RegistrationFormFields(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    firstName: String,
    lastName: String,
    email: String,
    invalidFields: List<OnboardingField>,
    updateFirstName: (String) -> Unit,
    updateLastName: (String) -> Unit,
    updateEmail: (String) -> Unit,
    whenDone: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = firstName,
            enabled = !isLoading,
            onValueChange = { updateFirstName(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.form_first_name))
            },
            isError = invalidFields.contains(OnboardingField.FIRST_NAME)
        )
        TextField(
            value = lastName,
            enabled = !isLoading,
            onValueChange = { updateLastName(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.form_last_name))
            },
            isError = invalidFields.contains(OnboardingField.LAST_NAME)
        )
        TextField(
            value = email,
            enabled = !isLoading,
            onValueChange = { updateEmail(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.form_email_address))
            },
            isError = invalidFields.contains(OnboardingField.EMAIL_ADDRESS),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { whenDone() }
            )
        )
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                CircularProgressIndicator()
            }
        }
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