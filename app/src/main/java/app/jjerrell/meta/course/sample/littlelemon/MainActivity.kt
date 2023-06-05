package app.jjerrell.meta.course.sample.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.jjerrell.meta.course.sample.littlelemon.navigation.enrollmentNavigation
import app.jjerrell.meta.course.sample.littlelemon.navigation.mainNavigation
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

const val DATA_STORE_NAME = "LittleLemonMenu"
val KEY_FIRST_NAME = stringPreferencesKey("firstName")
val KEY_LAST_NAME = stringPreferencesKey("lastName")
val KEY_EMAIL = stringPreferencesKey("email")

val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            LittleLemonTheme {
                NavHost(
                    navController = navController,
                    startDestination = AppFlow.LOGIN.route,
                    builder = {
                        enrollmentNavigation(controller = navController)
                        mainNavigation(controller = navController)
                    }
                )
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//
//                }
            }
        }
    }
}
