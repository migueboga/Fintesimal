package com.example.fintesimal.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fintesimal.data.db.dao.AddressDao
import com.example.fintesimal.data.db.entities.Address

@Database(
    entities = [
        Address::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAddress(): AddressDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "fintesimalDB.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}