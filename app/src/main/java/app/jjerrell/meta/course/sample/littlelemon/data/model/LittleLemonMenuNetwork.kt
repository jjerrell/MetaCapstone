package app.jjerrell.meta.course.sample.littlelemon.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LittleLemonMenuNetwork(
    @SerialName("menu")
    val menuItems: List<MenuItemNetwork>
)