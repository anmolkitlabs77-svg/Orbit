package com.orbit.dashboard.weather.model

import com.google.gson.annotations.SerializedName

data class weather(
    val messageType: String,
    @SerializedName("messageID")
    val messageId: String,
    @SerializedName("messageURL")
    val messageUrl: String,
    val messageIssueTime: String,
    val messageBody: String,
)


