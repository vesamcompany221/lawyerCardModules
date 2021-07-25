package com.vesam.barexamlibrary.data.model.response.buy_wallet


import com.google.gson.annotations.SerializedName

data class ResponseBuyWalletModel(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("wallet")
    val wallet: Int
)