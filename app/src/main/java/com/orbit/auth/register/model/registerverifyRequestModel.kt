package com.orbit.auth.register.model

data class RegisterVerifyRequest(
    val email: String,
    val credential: String
)