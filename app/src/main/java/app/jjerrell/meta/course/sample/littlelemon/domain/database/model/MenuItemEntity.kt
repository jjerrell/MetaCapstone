package app.jjerrell.meta.course.sample.littlelemon.domain.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("price") val price: String,
    @ColumnInfo("image") val image: String,
    @ColumnInfo("category") val category: String
)
