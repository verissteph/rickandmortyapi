package com.stephanieverissimo.rickandmortyapi

import com.stephanieverissimo.rickandmortyapi.data.api.CharacterApi
import com.stephanieverissimo.rickandmortyapi.data.model.Character
import com.stephanieverissimo.rickandmortyapi.data.model.CharacterResponse
import com.stephanieverissimo.rickandmortyapi.data.model.Info
import com.stephanieverissimo.rickandmortyapi.data.model.Location
import com.stephanieverissimo.rickandmortyapi.data.model.Origin
import com.stephanieverissimo.rickandmortyapi.domain.repository.CharacterRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterRepositoryTest {

    private lateinit var characterApi: CharacterApi
    val dispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        MockKAnnotations.init(this)
        characterApi = mockk<CharacterApi>()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private fun createMockCharacterResponse(): CharacterResponse {
        val character1 = Character(
            id = 1,
            name = "Character 1",
            image = "image1.jpg",
            status = "alive",
            species = "human",
            type = "_",
            gender = "male",
            origin = Origin("name", "url"),
            location = Location("name", "url"),
            episode = listOf("episode1", "episode2"),
            url = "url",
            created = "created"
        )
        val character2 = Character(
            id = 2,
            name = "Character 2",
            image = "image2.jpg",
            status = "dead",
            species = "alien",
            type = "_",
            gender = "female",
            origin = Origin("name", "url"),
            location = Location("name", "url"),
            episode = listOf("episode1", "episode2"),
            url = "url",
            created = "created"
        )
        val characters = listOf(character1, character2)

        return mockk<CharacterResponse>().apply {
            every { results } returns characters
            every { info } returns Info(
                count = characters.size,
                pages = 1,
                next = null,
                prev = null
            )
        }
    }

    @Test
    fun `getCharacters should return character response from characterApi`() = runBlocking {

        // Configuração do mock para o caso de sucesso
        val expectedResponse = createMockCharacterResponse()
        coEvery { characterApi.getCharacters(any()) } returns expectedResponse

        // Cria uma instância de CharacterRepositoryImpl com o mock de CharacterApi
        val characterRepository = CharacterRepositoryImpl(characterApi)

        // Chama a função de teste
        val actualResponse = characterRepository.getCharacters(1)

        // Verifica se a resposta é a esperada
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `getFilteredCharacters should return character response from characterApi`() = runBlocking {

        // Configuração do mock para o caso de sucesso
        val expectedResponse = createMockCharacterResponse()
        coEvery {
            characterApi.getFilteredCharacters(
                any(),
                any(),
                any()
            )
        } returns expectedResponse

        // Cria uma instância de CharacterRepositoryImpl com o mock de CharacterApi
        val characterRepository = CharacterRepositoryImpl(characterApi)

        // Chama a função de teste
        val actualResponse = characterRepository.getFilteredCharacters(1, "name", "status")

        // Verifica se a resposta é a esperada
        assertEquals(expectedResponse, actualResponse)
    }
}