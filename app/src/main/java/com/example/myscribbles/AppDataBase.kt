package com.example.myscribbles

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myscribbles.dao.EntryDao
import com.example.myscribbles.dao.ImageDao
import com.example.myscribbles.model.Entry
import com.example.myscribbles.model.Image

@Database(entities = [Entry::class, Image::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getEntryDao(): EntryDao
    abstract fun getImageDao(): ImageDao

    companion object {
        private const val DB_NAME = "note_database.db"

        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            DB_NAME
        ).build()
    }
}