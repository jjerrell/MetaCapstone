package app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.PageLoadingIndicator
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

/**
 * Checks if the user has registered or not. Calls onResult after checking status,
 * returning a boolean indicating registration status.
 *
 * @param onResult callback indicating registration status where `true` means that they are registered
 */
@Composable
fun CheckOnboardingPage(
    onResult: (isRegistered: Boolean) -> Unit
) {
    val viewModel = viewModel<CheckOnboardingViewModel>()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.lemon_bordered),
            contentDescription = null,
            modifier = Modifier.size(96.dp),
        )
        PageLoadingIndicator(
            modifier = Modifier
                .fillMaxHeight(0.3f),
            isLoading = viewModel.isLoading,
            onLoadingStateChange = {
                if (viewModel.isLoading) {
                    viewModel.checkRegistration(context = context)
                } else {
                    onResult(viewModel.isRegistered)
                }
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CheckOnboarding_Preview() {
    LittleLemonTheme {
        CheckOnboardingPage(
            onResult = {}
        )
    }
}