package com.orbit.prelogin.auth.register.viewModel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orbit.prelogin.auth.login.model.LoginVerifyResponse
import com.orbit.network.NetworkResult
import com.orbit.network.Repository
import com.orbit.prelogin.auth.register.model.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class registerVM @Inject constructor(val repository: Repository) : ViewModel() {


    private val _register =
        MutableLiveData<NetworkResult<LoginVerifyResponse>>()

    val register: LiveData<NetworkResult<LoginVerifyResponse>>
        get() = _register

    fun register(
        activity: Activity,
        request: RegisterRequest
    ) = viewModelScope.launch {

        _register.value = NetworkResult.Loading()

        _register.value = repository.register(
            activity = activity,
            request = request
        )
    }

}