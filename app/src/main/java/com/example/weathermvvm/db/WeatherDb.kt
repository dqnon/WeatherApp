package com.example.weathermvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherItem::class], version = 1)
abstract class WeatherDb: RoomDatabase() {
    abstract fun getDao(): Dao
    companion object{
        fun getDb(context: Context): WeatherDb{
            return Room.databaseBuilder(context, WeatherDb::class.java, "test.db").build()
        }
    }
}