package com.example.jetpackcomposearchi.dashboard.events.viewModel

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposearchi.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class eventsVM @Inject constructor(val repository: Repository): ViewModel() {

    val events = repository.getEvents()

}