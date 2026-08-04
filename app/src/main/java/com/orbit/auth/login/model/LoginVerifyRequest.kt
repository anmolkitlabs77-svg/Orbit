package com.orbit.auth.login.model

data class LoginVerifyRequest(

    val email: String,

    val credential: String

)