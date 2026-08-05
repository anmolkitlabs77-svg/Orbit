package com.orbit.prelogin.auth.login.model

data class LoginVerifyRequest(

    val email: String,

    val credential: String

)