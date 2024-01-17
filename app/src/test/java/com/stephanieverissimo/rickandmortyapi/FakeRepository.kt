package com.stephanieverissimo.rickandmortyapi

import com.stephanieverissimo.rickandmortyapi.data.model.Character
import com.stephanieverissimo.rickandmortyapi.data.model.Info
import com.stephanieverissimo.rickandmortyapi.data.model.Location
import com.stephanieverissimo.rickandmortyapi.data.model.Origin
import com.stephanieverissimo.rickandmortyapi.domain.repository.CharacterRepository

import com.stephanieverissimo.rickandmortyapi.data.model.CharacterResponse

class FakeRepository : CharacterRepository {
    
    override suspend fun getCharacters(page: Int): CharacterResponse {
        // Simula a resposta da API para a obtenção de personagens
        return CharacterResponse(
                info = Info(count = 2, pages = 1, next = null, prev = null),
                results = listOf(
                        Character(
                                id = 1,
                                name = "Character 1",
                                status = "alive",
                                species = "human",
                                type = "_",
                                gender = "male",
                                origin = Origin(name = "name", url = "url"),
                                location = Location(name = "name", url = "url"),
                                image = "image1.jpg",
                                episode = listOf("episode1", "episode2"),
                                url = "url",
                                created = "created"
                                 ),
                        Character(
                                id = 2,
                                name = "Character 2",
                                status = "dead",
                                species = "alien",
                                type = "_",
                                gender = "female",
                                origin = Origin(name = "name", url = "url"),
                                location = Location(name = "name", url = "url"),
                                image = "image2.jpg",
                                episode = listOf("episode1", "episode2"),
                                url = "url",
                                created = "created"
                                 )
                                )
                                )
    }
    
    override suspend fun getFilteredCharacters(page: Int, name: String?, status: String?): CharacterResponse {
        // Simula a resposta da API para a obtenção de personagens filtrados
        // Você pode personalizar isso conforme necessário para seus testes específicos
        return CharacterResponse(
                info = Info(count = 1, pages = 1, next = null, prev = null),
                results = listOf(
                        Character(
                                id = 1,
                                name = "Filtered Character 1",
                                status = "alive",
                                species = "human",
                                type = "_",
                                gender = "male",
                                origin = Origin(name = "name", url = "url"),
                                location = Location(name = "name", url = "url"),
                                image = "filtered_image1.jpg",
                                episode = listOf("filtered_episode1", "filtered_episode2"),
                                url = "filtered_url",
                                created = "filtered_created"
                                 )
                                )
                                )
    }
}

