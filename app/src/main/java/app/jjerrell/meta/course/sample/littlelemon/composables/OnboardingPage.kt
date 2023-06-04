package app.jjerrell.meta.course.sample.littlelemon.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

@Composable
@ExperimentalLayoutApi
fun OnboardingPage(
    modifier: Modifier = Modifier,
    onRegistration: (invalidFields: List<OnboardingField>) -> Unit
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
                text = "Tell us a little about yourself",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        FormFields(
            modifier = Modifier.fillMaxHeight().weight(1F),
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
                onRegistration(viewModel.invalidFields)
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            content = {
                Text("Register")
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun Heading(
    modifier: Modifier = Modifier,
    heroContent: @Composable BoxScope.() -> Unit
) {
    Column(modifier = modifier) {
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
                .fillMaxHeight(0.25F)
                .padding(vertical = 20.dp)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center,
            content = heroContent
        )
    }
}

@Composable
private fun FormFields(
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
                Text("First name")
            },
            isError = invalidFields.contains(OnboardingField.FIRST_NAME)
        )
        TextField(
            value = lastName,
            onValueChange = { updateLastName(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Last name")
            },
            isError = invalidFields.contains(OnboardingField.LAST_NAME)
        )
        TextField(
            value = email,
            onValueChange = { updateEmail(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email address")
            },
            isError = invalidFields.contains(OnboardingField.EMAIL_ADDRESS),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
@OptIn(ExperimentalLayoutApi::class)
fun Onboarding_Preview() {
    LittleLemonTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        OnboardingPage(
            onRegistration = { _ -> }
        )
    }
}