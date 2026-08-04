package com.orbit.auth.login.model

data class LoginResponse(
    val publicKey: PublicKey,
)

data class PublicKey(
    val challenge: String,
    val hints: List<Any?>,
    val rpId: String,
    val allowCredentials: List<AllowCredential>,
    val extensions: Map<String, Any>,
)

data class AllowCredential(
    val type: String,
    val id: String,
)
