package com.jaiswal.bitcointrends.models

import com.google.gson.annotations.SerializedName
import com.jaiswal.bitcointrends.models.response.ChartCoordinates

data class ChartData (
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("unit")
    val unit: String? = null,

    @field:SerializedName("period")
    val period: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("values")
    val values: List<ChartCoordinates?>? = null

)