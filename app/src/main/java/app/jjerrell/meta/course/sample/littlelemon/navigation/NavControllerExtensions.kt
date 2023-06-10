package app.jjerrell.meta.course.sample.littlelemon.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator

/**
 * Type safe navigation control for in-flow calls
 */
fun NavController.navigate(
    destination: AppFlowRoute,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) = navigate(
    route = destination.path,
    navOptions = navOptions,
    navigatorExtras = navigatorExtras
)

/**
 * Type safe navigation control for in-flow calls using a [NavOptionsBuilder]
 */
fun NavController.navigate(
    destination: AppFlowRoute,
    builder: NavOptionsBuilder.() -> Unit
) = navigate(route = destination.path, builder = builder)

/**
 * Type safe navigation control for launching a specific navigation module
 */
fun NavController.navigate(
    destination: AppFlow,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) = navigate(
    route = destination.route,
    navOptions = navOptions,
    navigatorExtras = navigatorExtras
)

/**
 * Type safe navigation control for launching a specific navigation module using a [NavOptionsBuilder]
 */
fun NavController.navigate(
    destination: AppFlow,
    builder: NavOptionsBuilder.() -> Unit
) = navigate(route = destination.route, builder = builder)