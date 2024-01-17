package com.stephanieverissimo.rickandmortyapi.data.api

import com.stephanieverissimo.rickandmortyapi.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("/api/character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse
    
    @GET("/api/character/")
    suspend fun getFilteredCharacters(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("status") status: String?
    ): CharacterResponse
    
}