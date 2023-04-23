package com.example.weathermvvm.domain.UseCase

import com.example.weathermvvm.domain.repository.FragmentRoomRepository

class SaveFragmentUseCase(private val fragmentRoomRepository: FragmentRoomRepository) {

    fun executeFragment(): FragmentRoomRepository{
        return fragmentRoomRepository
    }
}