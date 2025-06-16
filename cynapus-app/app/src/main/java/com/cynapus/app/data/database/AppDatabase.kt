package com.cynapus.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cynapus.app.data.converter.DateConverter
import com.cynapus.app.data.dao.MemoDao
import com.cynapus.app.data.dao.TodoDao
import com.cynapus.app.data.entity.MemoEntity
import com.cynapus.app.data.entity.TodoEntity

@Database(
    entities = [TodoEntity::class, MemoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun memoDao(): MemoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cynapus_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}