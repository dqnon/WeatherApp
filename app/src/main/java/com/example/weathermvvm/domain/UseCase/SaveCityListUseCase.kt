package com.example.weathermvvm.domain.UseCase

import com.example.weathermvvm.domain.repository.RoomRepository

class SaveCityListUseCase(private val roomRepository: RoomRepository) {

    fun executeRoom(): RoomRepository {
        return roomRepository
    }
}