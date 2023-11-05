package com.vision19.mathplus

import MIGRATION_1_2
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 2,
    entities = [UserAnswer::class],
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ],
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userAnswerDao(): UserAnswerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_answers_database"
                )
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2)
                .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}