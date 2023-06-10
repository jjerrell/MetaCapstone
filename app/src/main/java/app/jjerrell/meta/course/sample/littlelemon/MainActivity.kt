package app.jjerrell.meta.course.sample.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.jjerrell.meta.course.sample.littlelemon.navigation.AppFlow
import app.jjerrell.meta.course.sample.littlelemon.navigation.enrollmentNavigation
import app.jjerrell.meta.course.sample.littlelemon.navigation.mainNavigation
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

const val DATA_STORE_NAME = "LittleLemonMenu"
val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            LittleLemonTheme {
                NavHost(
                    navController = navController,
                    startDestination = AppFlow.LOGIN.route,
                    modifier = Modifier,
                    builder = {
                        enrollmentNavigation(controller = navController)
                        mainNavigation(controller = navController)
                    }
                )
            }
        }
    }
}
