package com.example.weathermvvm.di

import android.content.Context
import com.example.weathermvvm.domain.UseCase.*
import com.example.weathermvvm.domain.repository.GeoPositionRepository
import com.example.weathermvvm.domain.repository.RoomRepository
import com.example.weathermvvm.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetLocationUseCase(geoPositionRepository: GeoPositionRepository, @ApplicationContext context: Context): GetLocationUseCase{
        return GetLocationUseCase(geoPositionRepository, context)
    }

    @Provides
    fun provideSaveCityListUseCase(roomRepository: RoomRepository): SaveCityListUseCase{
        return SaveCityListUseCase(roomRepository)
    }

    @Provides
    fun provideGetSearchListUseCase(weatherRepository: WeatherRepository): GetSearchListUseCase{
        return GetSearchListUseCase(weatherRepository)
    }

    @Provides
    fun provideChangeBackgroundUseCase(): ChangeBackgroundUseCase{
        return ChangeBackgroundUseCase()
    }

    @Provides
    fun provideGetForecastUseCase(weatherRepository: WeatherRepository): GetForecastUseCase{
        return GetForecastUseCase(weatherRepository)
    }
}