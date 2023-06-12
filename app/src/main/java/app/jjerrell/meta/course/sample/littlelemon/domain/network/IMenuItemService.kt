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

fun provideMenuItemNetworkService(client: HttpClient): IMenuItemNetworkService =
    MenuItemNetworkService(httpClient = client)

private class MenuItemNetworkService(val httpClient: HttpClient) : IMenuItemNetworkService {
    override suspend fun getMenu(): LittleLemonMenuNetwork {
        // make the network request
        val request = httpClient.get(MENU_ENDPOINT)
        // unwrap the text because the payload is Content-Type: text/plain
        val jsonString = request.bodyAsText()
        // manually run the response through the Json decoder
        return Json.decodeFromString(jsonString)
    }
}