package app.jjerrell.meta.course.sample.littlelemon.ui.composables.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.BackIconNavItem
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLButton
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLTopAppBar
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.PageLoadingIndicator
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.ProfileIconNavItem
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.RegistrationFormFields

@ExperimentalComposeUiApi
@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    onBackAction: () -> Unit,
    whenLoggedOut: () -> Unit
) {
    val viewModel = viewModel<ProfileViewModel>()
    val context = LocalContext.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LLTopAppBar(
            navigationIcon = {
                BackIconNavItem(onBackAction)
            },
            actions = {
                ProfileIconNavItem(isEnabled = false)
            }
        )
        when (val currentState = viewModel.state) {
            null -> {
                PageLoadingIndicator(
                    isLoading = true,
                    onLoadingStateChange = {
                        viewModel.loadUserData(context = context)
                    }
                )
            }
            else -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, bottom = 30.dp)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    item {
                        Text(
                            "Personal Information",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    item {
                        RegistrationFormFields(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1F),
                            isEnabled = false,
                            firstName = currentState.firstName,
                            lastName = currentState.lastName,
                            email = currentState.email
                        )
                    }
                    item {
                        LLButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(horizontal = 20.dp),
                            onClick = {
                                viewModel.logout(context) {
                                    whenLoggedOut()
                                }
                            },
                            title = stringResource(R.string.logout_button)
                        )
                    }
                }
            }
        }
    }
}
