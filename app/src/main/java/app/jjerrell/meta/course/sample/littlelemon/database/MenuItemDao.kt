package app.jjerrell.meta.course.sample.littlelemon.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.jjerrell.meta.course.sample.littlelemon.database.model.MenuItemEntity

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu")
    fun getAll(): List<MenuItemEntity>

    @Insert
    fun insertAll(vararg items: MenuItemEntity)
}
