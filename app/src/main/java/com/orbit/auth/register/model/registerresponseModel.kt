package com.orbit.auth.register.model

data class RegisterResponse(
    val publicKey: PublicKey,
)

data class PublicKey(
    val rp: Rp,
    val user: User,
    val challenge: String,
    val pubKeyCredParams: List<PubKeyCredParam>,
    val hints: List<Any?>,
    val excludeCredentials: List<Any?>,
    val attestation: String,
    val extensions: Extensions,
)

data class Rp(
    val name: String,
    val id: String,
)

data class User(
    val name: String,
    val displayName: String,
    val id: String,
)

data class PubKeyCredParam(
    val alg: Long,
    val type: String,
)

data class Extensions(
    val credProps: Boolean,
)
