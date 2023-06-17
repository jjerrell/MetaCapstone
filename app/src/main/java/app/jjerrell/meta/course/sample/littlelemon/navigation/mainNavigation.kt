package app.jjerrell.meta.course.sample.littlelemon.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.main.MainPage
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.main.ProfilePage

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun NavGraphBuilder.mainNavigation(
    controller: NavController
) {
    appFlow(start = AppFlowRoute.MENU, route = AppFlow.MAIN) {
        page(appRoute = AppFlowRoute.MENU) {
            MainPage(
                modifier = Modifier.fillMaxSize(),
                onNavigateToProfile = {
                    controller.navigate(AppFlowRoute.PROFILE) {
                        launchSingleTop = true
                    }
                }
            )
        }
        page(appRoute = AppFlowRoute.PROFILE) {
            ProfilePage(
                modifier = Modifier.fillMaxSize(),
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
