package com.orbit.auth.login.viewModel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orbit.auth.login.model.LoginVerifyResponse
import com.orbit.network.NetworkResult
import com.orbit.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class loginVM @Inject constructor(val repository: Repository) : ViewModel() {


    private val _login =
        MutableLiveData<NetworkResult<LoginVerifyResponse>>()

    val login: LiveData<NetworkResult<LoginVerifyResponse>>
        get() = _login

    fun login(
        activity: Activity,
        email: String
    ) = viewModelScope.launch {

        _login.value = NetworkResult.Loading()

        _login.value = repository.login(
            activity,
            email)
    }

}
