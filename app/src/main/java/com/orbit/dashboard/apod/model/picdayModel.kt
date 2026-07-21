package com.orbit.dashboard.apod.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class picdayModel(
    val copyright: String? = null,
    val date: String,
    val explanation: String,
    val hdurl: String? = null,
    @SerialName("media_type")
    val mediaType: String,
    @SerialName(value = "service_version")
    val serviceVersion: String,
    val title: String,
    val url: String
)