package com.orbit.prelogin.auth.login.viewModel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.util.CoilUtils.result
import com.orbit.dashboard.base.App
import com.orbit.prelogin.auth.login.model.LoginVerifyResponse
import com.orbit.network.NetworkResult
import com.orbit.network.Repository
import com.orbit.other.Cons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class loginVM @Inject constructor(val repository: Repository) : ViewModel() {

    private val _email = MutableLiveData("")
    val email : LiveData<String> = _email
    private val _loginEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val loginEvent = _loginEvent.asSharedFlow()

    private val _displayLoader = MutableLiveData<Boolean>(false)
    val  displayLoader : LiveData<Boolean> = _displayLoader

    private val _login = MutableLiveData<NetworkResult<LoginVerifyResponse>>()

    val login: LiveData<NetworkResult<LoginVerifyResponse>>
        get() = _login

    fun updateEmail(Email: String){
        _email.value = Email
    }

    fun login(activity: Activity,) = viewModelScope.launch {

        _login.value = NetworkResult.Loading()
        _displayLoader.value = true

        val result = repository.login(
            activity,
            email.value
        )

        _login.value = result

        if (result is NetworkResult.Error) {
            _displayLoader.value = false
            _loginEvent.emit("Login failed. Please try again.")
        }
        if (result is NetworkResult.Success) {
            _displayLoader.value = false
            _loginEvent.emit("Login successful!")
            result.data?.email?.let { App.sharedPref.putString(Cons.EMAIL,it) }
            result.data?.name?.let { App.sharedPref.putString(Cons.NAME,it) }
        }
    }

}
