package app.jjerrell.meta.course.sample.littlelemon.domain.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LittleLemonMenuNetwork(
    @SerialName("menu")
    val menuItems: List<MenuItemNetwork>
)