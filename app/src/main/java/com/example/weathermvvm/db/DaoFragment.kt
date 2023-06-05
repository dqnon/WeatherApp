package com.example.weathermvvm.db

import androidx.room.*
import com.example.weathermvvm.domain.model.RoomModel

@Dao
interface DaoFragment {

    @Upsert()
    suspend fun saveFragment(fragmentData: RoomModel)

    @Query("SELECT * FROM weatherFragment")
    fun getAllFragments(): MutableList<RoomModel>

    @Query("DELETE FROM weatherFragment")
    fun clearTable()

    @Query("SELECT * FROM weatherFragment WHERE locationName = :city")
    fun getCityByName(city: String): RoomModel

//    @Query("UPDATE weatherFragment SET city = :item WHERE :geoLocation = 1")
//    suspend fun updateGeolocation(item: String, geoLocation: Int)
}