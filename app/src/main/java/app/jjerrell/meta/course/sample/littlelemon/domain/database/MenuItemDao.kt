package app.jjerrell.meta.course.sample.littlelemon.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.jjerrell.meta.course.sample.littlelemon.domain.database.model.MenuItemEntity

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_items")
    fun getAll(): List<MenuItemEntity>

    @Insert
    fun insertAll(vararg items: MenuItemEntity)
}
