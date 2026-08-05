package com.orbit.prelogin.auth.register.model

data class RegisterVerifyRequest(
    val email: String,
    val credential: String
)