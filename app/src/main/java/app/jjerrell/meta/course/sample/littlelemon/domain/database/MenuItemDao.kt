package app.jjerrell.meta.course.sample.littlelemon.domain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.jjerrell.meta.course.sample.littlelemon.domain.database.model.MenuItemEntity

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_items")
    suspend fun getAll(): List<MenuItemEntity>

    @Insert
    suspend fun insertAll(vararg items: MenuItemEntity)

    @Delete
    suspend fun delete(model: MenuItemEntity)

    @Query("DELETE FROM menu_items")
    suspend fun deleteAll()
}
