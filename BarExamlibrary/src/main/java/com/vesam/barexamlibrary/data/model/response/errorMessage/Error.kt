package com.vesam.barexamlibrary.data.model.response.errorMessage


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("message")
    val message: String
)