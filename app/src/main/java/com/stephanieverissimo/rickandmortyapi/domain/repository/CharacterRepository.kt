package com.stephanieverissimo.rickandmortyapi.domain.repository

import com.stephanieverissimo.rickandmortyapi.data.api.CharacterApi
import com.stephanieverissimo.rickandmortyapi.data.model.CharacterResponse

interface CharacterRepository {
    suspend fun getCharacters(page: Int): CharacterResponse
    suspend fun getFilteredCharacters(page: Int, name: String?, status: String?): CharacterResponse
}

class CharacterRepositoryImpl(private val characterApi: CharacterApi) : CharacterRepository {
    override suspend fun getCharacters(page: Int): CharacterResponse {
        return characterApi.getCharacters(page)
    }
    
    override suspend fun getFilteredCharacters(page: Int, name: String?, status: String?): CharacterResponse {
        return characterApi.getFilteredCharacters(page, name, status)
    }
}



