package com.lokalook.lokalook.data.remote.response
import com.google.gson.annotations.SerializedName

/**
 * Response model for user registration request.
 * @property isError Indicates whether the registration request was successful or not.
 * @property message Describes the result of the registration request.
 */
data class RegisterResponse(

    @field:SerializedName("error")
    val isError: Boolean,

    @field:SerializedName("message")
    val message: String
)