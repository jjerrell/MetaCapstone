package app.jjerrell.meta.course.sample.littlelemon.network

import app.jjerrell.meta.course.sample.littlelemon.network.model.LittleLemonMenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val MENU_ENDPOINT = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"

interface IMenuItemNetworkService {
    suspend fun getMenu(): LittleLemonMenuNetwork
}

class MenuItemNetworkService(private val httpClient: HttpClient) : IMenuItemNetworkService {
    override suspend fun getMenu(): LittleLemonMenuNetwork {
        return httpClient.get(MENU_ENDPOINT).body()
    }
}