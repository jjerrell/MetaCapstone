package app.jjerrell.meta.course.sample.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.jjerrell.meta.course.sample.littlelemon.AppFlow
import app.jjerrell.meta.course.sample.littlelemon.AppFlowRoute

/**
 * Replacement for [NavGraphBuilder.navigation] which allows for creating type-safe navigation flows
 *
 * @param start the first page for the modular flow
 * @param route the route used in navigation controls to invoke this flow
 * @param builder the builder used to construct the graph
 * @receiver
 */
fun NavGraphBuilder.appFlow(
    start: AppFlowRoute,
    route: AppFlow,
    builder: NavGraphBuilder.() -> Unit
) = navigation(
    startDestination = start.path,
    route = route.route,
    builder = builder
)

/**
 * Replacement for [NavGraphBuilder.composable] which allows type-safe navigation design and routing.
 *
 * @param appRoute the route used to navigate to this composable definition
 * @param arguments list of arguments to associate with destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param content composable for the destination
 */
fun NavGraphBuilder.page(
    appRoute: AppFlowRoute,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(
    route = appRoute.path,
    arguments = arguments,
    deepLinks = deepLinks,
    content = content
)