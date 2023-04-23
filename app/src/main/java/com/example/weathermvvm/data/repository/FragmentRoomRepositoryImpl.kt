package com.example.weathermvvm.data.repository

import com.example.weathermvvm.db.CityList.Dao
import com.example.weathermvvm.db.DaoFragment
import com.example.weathermvvm.db.FragmentItem
import com.example.weathermvvm.domain.model.RoomModel
import com.example.weathermvvm.domain.model.forecast.Forecast
import com.example.weathermvvm.domain.repository.FragmentRoomRepository

class FragmentRoomRepositoryImpl(private val weatherDao: DaoFragment): FragmentRoomRepository {
    override val allFragments: MutableList<RoomModel>
        get() = weatherDao.getAllFragments()

    override suspend fun saveFragment(frItem: RoomModel) {
        weatherDao.saveFragment(frItem)
    }
}