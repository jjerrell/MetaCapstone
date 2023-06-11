package app.jjerrell.meta.course.sample.littlelemon.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding.CheckOnboarding
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.onboarding.OnboardingPage

/**
 * Enrollment navigation "module". Begins with the  [CheckOnboarding] view where the app
 * decides whether to ask the user to register or to move on to the [mainNavigation] module
 * because the app determines that the user is registered
 *
 * @param controller the NavHostController for the application
 */
@ExperimentalComposeUiApi
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.enrollmentNavigation(controller: NavController) {
    // A straightforward implementation of the navigation API
    // used to enable type safe navigation graphs and calls.
    appFlow(start = AppFlowRoute.CHECK_ONBOARDING, route = AppFlow.LOGIN) {
        // The immediate loading page (if visible at all) which checks
        // registration status to determine the actual starting route
        // for the users experience
        page(appRoute = AppFlowRoute.CHECK_ONBOARDING) {
            CheckOnboarding(onResult = { isRegistered: Boolean ->
                if (isRegistered) {
                    controller.navigate(AppFlow.MAIN)
                } else {
                    controller.navigate(AppFlowRoute.REGISTRATION)
                }
            })
        }
        // The user registration (aka "Onboarding") page
        page(appRoute = AppFlowRoute.REGISTRATION) {
            OnboardingPage(
                onRegistrationSuccess = {
                    controller.navigate(AppFlow.MAIN)
                }
            )
        }
    }
}
