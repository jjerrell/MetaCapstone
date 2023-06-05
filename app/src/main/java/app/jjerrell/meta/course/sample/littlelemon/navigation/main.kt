package app.jjerrell.meta.course.sample.littlelemon.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import app.jjerrell.meta.course.sample.littlelemon.AppFlow
import app.jjerrell.meta.course.sample.littlelemon.AppFlowRoute

fun NavGraphBuilder.mainNavigation(controller: NavController) {
    appFlow(start = AppFlowRoute.MENU, route = AppFlow.MAIN) {
        page(appRoute = AppFlowRoute.MENU) {
            Text("Todo")
        }
    }
}