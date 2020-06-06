package com.example.mycartapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Customer::class], version = 1)
abstract class CustomerDatabase : RoomDatabase() {
    abstract val customerDao: Dao

    companion object {
        @Volatile
        private var INSTANCE: CustomerDatabase? = null
        fun getInstance(context: Context): CustomerDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CustomerDatabase::class.java,
                        "customer_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}