package com.example.jetpackcomposearchi.dashboard.weather.viewModel

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposearchi.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class weatherVM @Inject constructor(val respository: Repository): ViewModel() {

    val weather = respository.getWeather()

}