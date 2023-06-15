package app.jjerrell.meta.course.sample.littlelemon.ui.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding.OnboardingField

/**
 * Collection of form fields used to register the user for the application
 *
 * Uses the [state hoisting](https://developer.android.com/jetpack/compose/state#state-hoisting)
 * pattern to perform read/write activities.
 */
@Composable
@ExperimentalComposeUiApi
fun RegistrationFormFields(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    firstName: String,
    lastName: String,
    email: String,
    invalidFields: List<OnboardingField> = emptyList(),
    updateFirstName: (String) -> Unit = {},
    updateLastName: (String) -> Unit = {},
    updateEmail: (String) -> Unit = {},
    whenDone: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = firstName,
            enabled = isEnabled,
            onValueChange = { updateFirstName(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.form_first_name),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            isError = invalidFields.contains(OnboardingField.FIRST_NAME)
        )
        TextField(
            value = lastName,
            enabled = isEnabled,
            onValueChange = { updateLastName(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.form_last_name),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            isError = invalidFields.contains(OnboardingField.LAST_NAME)
        )
        TextField(
            value = email,
            enabled = isEnabled,
            onValueChange = { updateEmail(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.form_email_address),
                    style = MaterialTheme.typography.labelLarge
                )
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
    }
}