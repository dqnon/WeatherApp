package com.example.weathermvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weathermvvm.data.Converters
import com.example.weathermvvm.db.CityList.Dao
import com.example.weathermvvm.db.CityList.WeatherCityItem
import com.example.weathermvvm.domain.model.RoomModel

@Database(entities = [WeatherCityItem::class, RoomModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDb: RoomDatabase() {
    abstract fun getDao(): Dao

    abstract fun getFragmentDao(): DaoFragment
    companion object{
        fun getDb(context: Context): WeatherDb{
            return Room.databaseBuilder(context, WeatherDb::class.java, "test.db").build()
        }


    }
}