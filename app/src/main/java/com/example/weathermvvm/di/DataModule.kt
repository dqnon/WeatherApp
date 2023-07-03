package com.example.weathermvvm.di

import android.content.Context
import com.example.weathermvvm.data.repository.GeoPositionRepositoryImpl
import com.example.weathermvvm.data.repository.RoomRepositoryImpl
import com.example.weathermvvm.data.repository.WeatherRepositoryImpl
import com.example.weathermvvm.db.CityList.Dao
import com.example.weathermvvm.db.WeatherDb
import com.example.weathermvvm.domain.repository.GeoPositionRepository
import com.example.weathermvvm.domain.repository.RoomRepository
import com.example.weathermvvm.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Dao {
        return WeatherDb.getDb(context).getDao()
    }

    @Provides
    @Singleton
    fun provideRoomRepository(dataBase: Dao): RoomRepository{
        return RoomRepositoryImpl(dataBase)
    }

    @Provides
    @Singleton
    fun provideGeoPositionRepository(): GeoPositionRepository {
        return GeoPositionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl()
    }
}