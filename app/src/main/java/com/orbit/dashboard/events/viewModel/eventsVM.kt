package com.orbit.dashboard.events.viewModel

import androidx.lifecycle.ViewModel
import com.orbit.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class eventsVM @Inject constructor(val repository: Repository): ViewModel() {

    val events = repository.getEvents()

}