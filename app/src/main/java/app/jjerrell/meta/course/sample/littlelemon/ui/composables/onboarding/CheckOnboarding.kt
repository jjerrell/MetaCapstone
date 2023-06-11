package app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.PageLoadingIndicator

/**
 * Checks if the user has registered or not. Calls onResult after checking status,
 * returning a boolean indicating registration status.
 *
 * @param modifier default modifier to be applied to the parent in this composable
 * @param onResult callback indicating registration status where `true` means that they are registered
 */
@Composable
fun CheckOnboarding(
    modifier: Modifier = Modifier,
    onResult: (isRegistered: Boolean) -> Unit
) {
    val viewModel = viewModel<CheckOnboardingViewModel>()
    val context = LocalContext.current
    PageLoadingIndicator(
        modifier = modifier,
        onInitialize = {
            if (viewModel.isLoading) {
                viewModel.checkRegistration(context = context)
            } else {
                onResult(viewModel.isRegistered)
            }
        }
    )
}
