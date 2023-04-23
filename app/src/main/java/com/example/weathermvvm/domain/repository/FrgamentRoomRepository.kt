package com.example.weathermvvm.domain.repository

import com.example.weathermvvm.db.FragmentItem
import com.example.weathermvvm.domain.model.RoomModel

interface FragmentRoomRepository {

    val allFragments: MutableList<RoomModel>

    suspend fun saveFragment(frItem: RoomModel)
}