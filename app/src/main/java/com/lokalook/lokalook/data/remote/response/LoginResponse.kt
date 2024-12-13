package com.lokalook.lokalook.data.remote.response

/**
 * Response model for login request.
 * @property loginResult Contains the details of the logged-in user.
 * @property isError Indicates whether the request was successful or not.
 * @property message Describes the result of the login request.
 */
data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult?
)

data class LoginResult(
    val userId: String,
    val token: String
)