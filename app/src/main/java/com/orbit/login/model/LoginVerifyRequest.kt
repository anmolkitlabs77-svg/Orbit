package com.orbit.login.model

data class LoginVerifyRequest(

    val email: String,

    val credential: String

)