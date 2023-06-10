package app.jjerrell.meta.course.sample.littlelemon.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.jjerrell.meta.course.sample.littlelemon.database.model.MenuItemEntity

@Database(entities = [MenuItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): MenuItemDao

    companion object {
        // singleton instance for the database
        private var INSTANCE: AppDatabase? = null

        /**
         * Get database singleton instance
         */
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            "ll_database"
                        )
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}