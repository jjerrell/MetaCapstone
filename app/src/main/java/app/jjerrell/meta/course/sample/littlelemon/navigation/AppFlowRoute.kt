package app.jjerrell.meta.course.sample.littlelemon.navigation

/**
 * Definitions for a specific navigation hierarchy within the app
 */
@JvmInline
value class AppFlow private constructor(val route: String) {
    companion object {
        val LOGIN: AppFlow = AppFlow("login")
        val MAIN: AppFlow = AppFlow("main")
    }
}

/**
 * Definitions for all pages in the app which can be navigated to
 *
 * Note that a truly modular app structure would instead provide an interface where each module could
 * provide an implementation. For the purposes of this app, a value class provides a very lightweight
 * solution
 */
@JvmInline
value class AppFlowRoute private constructor(val path: String) {
    companion object {
        val CHECK_ONBOARDING: AppFlowRoute = AppFlowRoute("registration/check")
        val REGISTRATION: AppFlowRoute = AppFlowRoute("registration")
        val MENU: AppFlowRoute = AppFlowRoute("menu")
        val PROFILE: AppFlowRoute = AppFlowRoute("profile")
    }
}
