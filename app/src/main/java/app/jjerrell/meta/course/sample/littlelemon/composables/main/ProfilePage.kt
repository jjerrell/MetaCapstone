package app.jjerrell.meta.course.sample.littlelemon.composables.main

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.composables.components.BackIconNavItem
import app.jjerrell.meta.course.sample.littlelemon.composables.components.LLTopAppBar
import app.jjerrell.meta.course.sample.littlelemon.composables.components.ProfileIconNavItem
import app.jjerrell.meta.course.sample.littlelemon.composables.onboarding.OnboardingField
import app.jjerrell.meta.course.sample.littlelemon.domain.network.model.UserRegistration
import app.jjerrell.meta.course.sample.littlelemon.domain.database.provideUserDataSource
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    var state: UserRegistration? by mutableStateOf(null)
        private set

    fun loadUserData(context: Context) {
        val dataService = provideUserDataSource(context)
        viewModelScope.launch {
            dataService.userData.collect {
                state = it
            }
        }
    }

    fun logout(context: Context, whenCleared: () -> Unit) {
        val dataService = provideUserDataSource(context)
        viewModelScope.launch {
            dataService.logout(whenCleared)
        }
    }
}

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
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LaunchedEffect(key1 = Unit) {
                        viewModel.loadUserData(context = context)
                    }
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
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
                    items(OnboardingField.values().asList()) {
                        val (fieldName, textValue) = when (it) {
                            OnboardingField.FIRST_NAME -> "First name" to currentState.firstName
                            OnboardingField.LAST_NAME -> "Last name" to currentState.lastName
                            OnboardingField.EMAIL_ADDRESS -> "Email" to currentState.email
                        }
                        Text(fieldName)
                        TextField(value = textValue, onValueChange = {}, enabled = false)
                    }
                    item {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(horizontal = 20.dp),
                            onClick = {
                                viewModel.logout(context) {
                                    whenLoggedOut()
                                }
                            },
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            content = {
                                Text("Logout")
                            }
                        )
                    }
                }
            }
        }
    }
}
