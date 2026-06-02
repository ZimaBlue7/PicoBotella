package com.example.picobotella.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.picobotella.data.local.dao.RetoDao
import com.example.picobotella.data.local.entities.RetoEntity

@Database(entities = [RetoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun retoDao(): RetoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pico_botella_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
