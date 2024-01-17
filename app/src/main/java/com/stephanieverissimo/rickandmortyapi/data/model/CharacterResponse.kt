package com.stephanieverissimo.rickandmortyapi.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val results: List<Character>,
)
