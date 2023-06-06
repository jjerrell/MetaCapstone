package app.jjerrell.meta.course.sample.littlelemon.composables.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.composables.components.Heading
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

@ExperimentalMaterial3Api
@Composable
@ExperimentalLayoutApi
fun OnboardingPage(
    modifier: Modifier = Modifier,
    onRegistrationSuccess: () -> Unit
) {
    val viewModel: OnboardingViewModel = viewModel()
    Column(
        modifier = modifier
            .imePadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Heading {
            Text(
                text = stringResource(R.string.registration_hero),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        RegistrationFormFields(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F),
            firstName = viewModel.state.firstName,
            lastName = viewModel.state.lastName,
            email = viewModel.state.emailAddress,
            invalidFields = viewModel.invalidFields.toList(),
            updateFirstName = { viewModel.update(OnboardingField.FIRST_NAME, it) },
            updateLastName = { viewModel.update(OnboardingField.LAST_NAME, it) },
            updateEmail = { viewModel.update(OnboardingField.EMAIL_ADDRESS, it) }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 20.dp),
            onClick = {
                viewModel.register()
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            content = {
                Text(stringResource(R.string.button_register))
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
    /**
     * Waits for the registrationFailed state value to be set to true and presents a dialog explaining
     * which fields are invalid for a successful registration. Invalid fields will be highlighted when
     * the user closes the dialog via implicit or explicit dismissal.
     */
    when (viewModel.state.registrationFailed) {
        true -> FailedRegistrationAlert(
            invalidFields = viewModel.invalidFields,
            onClose = { viewModel.resetRegistration() }
        )
        false -> {}
    }
    /**
     * Waits for the isRegistered state value to be set to true and then calls the onSuccess callback.
     */
    when (viewModel.state.isRegistered) {
        true -> onRegistrationSuccess()
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
private fun RegistrationFormFields(
    modifier: Modifier = Modifier,
    firstName: String,
    lastName: String,
    email: String,
    invalidFields: List<OnboardingField>,
    updateFirstName: (String) -> Unit,
    updateLastName: (String) -> Unit,
    updateEmail: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = firstName,
            onValueChange = { updateFirstName(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.form_first_name))
            },
            isError = invalidFields.contains(OnboardingField.FIRST_NAME)
        )
        TextField(
            value = lastName,
            onValueChange = { updateLastName(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.form_last_name))
            },
            isError = invalidFields.contains(OnboardingField.LAST_NAME)
        )
        TextField(
            value = email,
            onValueChange = { updateEmail(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.form_email_address))
            },
            isError = invalidFields.contains(OnboardingField.EMAIL_ADDRESS),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email
            )
        )
    }
}

@Composable
@ExperimentalMaterial3Api
private fun FailedRegistrationAlert(
    modifier: Modifier = Modifier,
    invalidFields: List<OnboardingField>,
    onClose: () -> Unit
) {
    AlertDialog(onDismissRequest = onClose) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(12),
            tonalElevation = 4.dp
        ) {
            Column(Modifier.padding(10.dp)) {
                Text(stringResource(R.string.form_input_invalid))
                invalidFields.forEach {
                    when (it) {
                        OnboardingField.FIRST_NAME -> Text(stringResource(R.string.form_first_name_invalid))
                        OnboardingField.LAST_NAME -> Text(stringResource(R.string.form_last_name_invalid))
                        OnboardingField.EMAIL_ADDRESS -> Text(stringResource(R.string.form_email_invalid))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onClose
                    ) {
                        Text(stringResource(R.string.button_close))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
fun Onboarding_Preview() {
    LittleLemonTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        OnboardingPage(
            onRegistrationSuccess = { }
        )
    }
}