package app.jjerrell.meta.course.sample.littlelemon.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.main.MainPage
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.main.ProfilePage

@ExperimentalFoundationApi
fun NavGraphBuilder.mainNavigation(
    controller: NavController
) {
    appFlow(start = AppFlowRoute.MENU, route = AppFlow.MAIN) {
        page(appRoute = AppFlowRoute.MENU) {
            MainPage(
                onNavigateToProfile = {
                    controller.navigate(AppFlowRoute.PROFILE) {
                        launchSingleTop = true
                    }
                }
            )
        }
        page(appRoute = AppFlowRoute.PROFILE) {
            ProfilePage(
                onBackAction = {
                    controller.popBackStack()
                },
                whenLoggedOut = {
                    controller.navigate(AppFlow.LOGIN)
                }
            )
        }
    }
}
