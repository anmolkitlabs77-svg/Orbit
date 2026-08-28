package com.orbit.prelogin.auth.register.viewModel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orbit.dashboard.base.App
import com.orbit.prelogin.auth.login.model.LoginVerifyResponse
import com.orbit.network.NetworkResult
import com.orbit.network.Repository
import com.orbit.other.Cons
import com.orbit.prelogin.auth.register.model.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class registerVM @Inject constructor(val repository: Repository) : ViewModel() {

    private val _email = MutableLiveData("")
    val email : LiveData<String> = _email

    private val _name = MutableLiveData("")
    val name : LiveData<String> = _name

    private val _displayLoader = MutableLiveData<Boolean>(false)
    val  displayLoader : LiveData<Boolean> = _displayLoader

    private val _registerEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val registerEvent = _registerEvent.asSharedFlow()

    fun updateEmail(email: String) { _email.value = email }
    fun updateName(name: String) { _name.value = name }

    private val _register = MutableLiveData<NetworkResult<LoginVerifyResponse>>()

    val register: LiveData<NetworkResult<LoginVerifyResponse>>
        get() = _register

    fun register(activity: Activity,) = viewModelScope.launch {

        _register.value = NetworkResult.Loading()
        _displayLoader.value = true


        val result = repository.register(
            activity = activity,
            request = RegisterRequest(_email.value,_name.value)
        )


        _register.value = result


        if (result is NetworkResult.Error) {
            _displayLoader.value = false
            _registerEvent.emit("SignUp failed. Please try again.")
        }
        if (result is NetworkResult.Success) {
            _displayLoader.value = false
            _registerEvent.emit("SignUp successful!")
            result.data?.name?.let { App.sharedPref.putString(Cons.NAME,it) }
            result.data?.email?.let { App.sharedPref.putString(Cons.EMAIL,it) }
        }

    }

}