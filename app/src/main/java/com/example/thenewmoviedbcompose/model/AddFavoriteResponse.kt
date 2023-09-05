package com.example.thenewmoviedbcompose.model

import com.google.gson.annotations.SerializedName

data class AddFavoriteResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
)