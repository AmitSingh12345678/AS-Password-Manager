package com.example.aspasswordmanager.ui.home.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = arrayOf(PasswordEntity::class),version=1,exportSchema = false)
abstract class PasswordDatabase: RoomDatabase() {

    abstract fun passwordDao(): PasswordDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PasswordDatabase? = null

        fun getDatabase(context: Context): PasswordDatabase {
// if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PasswordDatabase::class.java,
                    "password_table"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }

        }
    }
}