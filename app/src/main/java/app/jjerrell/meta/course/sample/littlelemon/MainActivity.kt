package app.jjerrell.meta.course.sample.littlelemon

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// User preferences data store
const val DATA_STORE_NAME = "LittleLemonMenu"
val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

// Ktor setup and config
private const val HTTP_TIME_OUT = 60_000

private val ktorHttpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })

        engine {
            connectTimeout = HTTP_TIME_OUT
            socketTimeout = HTTP_TIME_OUT
        }
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger Ktor =>", message)
            }

        }
        level = LogLevel.ALL
    }

    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status:", "${response.status.value}")
        }
    }

    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}

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
