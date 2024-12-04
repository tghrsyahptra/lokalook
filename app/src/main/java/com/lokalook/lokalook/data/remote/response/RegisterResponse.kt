package com.lokalook.lokalook.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("userId")
    val userId: String? = null
)