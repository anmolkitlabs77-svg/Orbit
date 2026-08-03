package com.orbit.login.viewModel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orbit.login.model.LoginVerifyResponse
import com.orbit.network.NetworkResult
import com.orbit.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class loginVM @Inject constructor(val repository: Repository) : ViewModel() {


    private val _register =
        MutableLiveData<NetworkResult<LoginVerifyResponse>>()

    val register: LiveData<NetworkResult<LoginVerifyResponse>>
        get() = _register

    fun login(
        activity: Activity,
        email: String
    ) = viewModelScope.launch {

        _register.value = NetworkResult.Loading()

        _register.value = repository.login(
            activity,
            email)
    }

}
