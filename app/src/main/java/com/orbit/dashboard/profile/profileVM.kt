package com.orbit.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orbit.network.NetworkResult
import com.orbit.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class profileVM @Inject constructor(val repository: Repository) : ViewModel() {

    private val _deleteEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val deleteEvent = _deleteEvent.asSharedFlow()

    private val _displayLoader = MutableLiveData<Boolean>(false)
    val  displayLoader : LiveData<Boolean> = _displayLoader

    fun deleteAccount(email: String) = viewModelScope.launch {

        _displayLoader.value = true

        val result = repository.deleteAccount(email)

        if (result is NetworkResult.Error) {
            _displayLoader.value = false
            _deleteEvent.emit(result.data?.message ?: "Account delection cause problem please try again.")
        }
        if (result is NetworkResult.Success) {
            _displayLoader.value = false
            _deleteEvent.emit(result.data?.message ?: "Account Successfully delected")
        }
    }
}