package com.vesam.barexamlibrary.data.model.response.errorMessage

import com.google.gson.annotations.SerializedName
import com.vesam.barexamlibrary.data.model.response.errorMessage.Error

class ResponseErrorMessageModel(
    @SerializedName("error")
    val error: Error,
)