package com.example.weathermvvm.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weathermvvm.domain.model.RoomModel

@Dao
interface DaoFragment {

    @Insert
    suspend fun saveFragment(fragmentData: RoomModel)

    @Query("SELECT * FROM weatherFragment")
    fun getAllFragments(): MutableList<RoomModel>
}