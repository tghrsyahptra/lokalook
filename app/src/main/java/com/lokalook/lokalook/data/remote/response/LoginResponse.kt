package com.lokalook.lokalook.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Response model for login request.
 * @property loginResult Contains the details of the logged-in user.
 * @property isError Indicates whether the request was successful or not.
 * @property message Describes the result of the login request.
 */
data class LoginResponse(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("error")
    val isError: Boolean,

    @field:SerializedName("message")
    val message: String
)

/**
 * Represents the result of a successful login.
 * @property name The name of the logged-in user.
 * @property userId The unique identifier for the user.
 * @property token The authentication token for the logged-in user.
 */
data class LoginResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)