package app.jjerrell.meta.course.sample.littlelemon.domain.network

import app.jjerrell.meta.course.sample.littlelemon.domain.network.model.LittleLemonMenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

private const val MENU_ENDPOINT = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"

interface IMenuItemNetworkService {
    suspend fun getMenu(): LittleLemonMenuNetwork
}

class MenuItemNetworkService(private val httpClient: HttpClient) : IMenuItemNetworkService {
    override suspend fun getMenu(): LittleLemonMenuNetwork {
        val request = httpClient.get(MENU_ENDPOINT)
        val jsonString = request.bodyAsText()
        return Json.decodeFromString(jsonString)
    }
}