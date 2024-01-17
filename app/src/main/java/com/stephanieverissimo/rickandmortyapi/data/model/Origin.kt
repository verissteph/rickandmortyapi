package com.stephanieverissimo.rickandmortyapi.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Origin(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)
