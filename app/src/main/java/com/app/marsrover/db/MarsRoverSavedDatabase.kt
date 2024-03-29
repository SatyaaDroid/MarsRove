package com.app.marsrover.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [MarsRoverSavedLocalModel::class],
    version = 1,
    exportSchema = false
)
abstract class MarsRoverSavedDatabase : RoomDatabase() {

    abstract fun marsRoverSavedPhotoDao(): MarsRoverSavedPhotoDao

    companion object {

        @Volatile
        private var INSTANCE: MarsRoverSavedDatabase? = null


        fun getInstance(context: Context): MarsRoverSavedDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabse(context).also { INSTANCE = it }
            }

        private fun buildDatabse(applicationContext: Context) =
            Room.databaseBuilder(
                applicationContext,
                MarsRoverSavedDatabase::class.java, "marsRover.db"
            )
                .fallbackToDestructiveMigration()
                .build()

    }
}