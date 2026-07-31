package com.orbit.register.model

data class RegisterVerifyRequest(
    val email: String,
    val credential: String
)