package app.jjerrell.meta.course.sample.littlelemon.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.composables.components.LLTopAppBar
import app.jjerrell.meta.course.sample.littlelemon.composables.main.MainPage

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
            Scaffold(
                topBar = {
                    LLTopAppBar(
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    controller.navigate(AppFlowRoute.MENU) {
                                        launchSingleTop = true
                                    }
                                }
                            ) {
                                Image(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Go back"
                                )
                            }
                        },
                        actions = {
                            Image(
                                painter = painterResource(id = R.drawable.profile_circle),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp, 24.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    )
                }
            ) {
                Text("TODO: Profile page")
            }
        }
    }
}