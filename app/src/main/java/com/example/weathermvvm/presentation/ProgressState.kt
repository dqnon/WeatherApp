package com.example.weathermvvm.presentation

sealed class ProgressState{
    object Loading: ProgressState()
    object Success: ProgressState()
    object Error: ProgressState()
}