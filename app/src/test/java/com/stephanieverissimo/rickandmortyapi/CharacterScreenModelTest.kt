package com.stephanieverissimo.rickandmortyapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stephanieverissimo.rickandmortyapi.data.model.Character
import com.stephanieverissimo.rickandmortyapi.data.model.CharacterResponse
import com.stephanieverissimo.rickandmortyapi.data.model.Info
import com.stephanieverissimo.rickandmortyapi.data.model.Location
import com.stephanieverissimo.rickandmortyapi.data.model.Origin
import com.stephanieverissimo.rickandmortyapi.domain.repository.CharacterRepository
import com.stephanieverissimo.rickandmortyapi.presentation.viewModel.CharacterScreenModel
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.stopKoin

@RunWith(JUnit4::class)
class CharacterScreenModelTest {
    
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    
    private val dispatcher = StandardTestDispatcher()
    
    @MockK()
    private lateinit var repository: CharacterRepository
    
    private lateinit var characterScreenModel: CharacterScreenModel
    
    private val expectedCharacters = listOf(
        Character(
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
                 ), Character(
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
                                           )
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(dispatcher)
        characterScreenModel = CharacterScreenModel(repository)
        
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }
    
    private fun createMockCharacterResponse(characters: List<Character>): CharacterResponse {
        return CharacterResponse(
            info = Info(count = characters.size, pages = 1, next = null, prev = null),
            results = characters
                                )
    }
    
    @Test
    fun `loadCharacters should update characters flow on success`(): Unit = runTest {
        // Configurar o mock para retornar o resultado esperado
        val expectedCharacters = listOf(
            Character(
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
                     ), Character(
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
                                       )
      coEvery { repository.getCharacters(any()) } returns (
          createMockCharacterResponse(
              expectedCharacters
                                     )
                                                          )
        // Chamar a função
        characterScreenModel.loadCharacters(1)
        
       
        coVerify() { repository.getCharacters(1) }
        
        // Verificar se o flow de error está nulo após o carregamento bem-sucedido
        assertNull(characterScreenModel.error.value)
        
        // Verificar se o flow de characters foi atualizado corretamente
        characterScreenModel.characters.collect { characters ->
            assertEquals(expectedCharacters, characters)
        }
    }
    
    @Test
    fun `loadCharacters should update error flow on failure`() = runTest {
        // Configuração do mock para simular um erro
        val errorMessage = "Failed to load characters"
        coEvery { repository.getCharacters(any()) } throws Exception(errorMessage)
        
        // Chamar a função
        characterScreenModel.loadCharacters(1)
        
        // Verificar se o flow de error foi atualizado corretamente
        assertEquals(errorMessage, characterScreenModel.error.value)
        // Verificar se characters continua vazio após o carregamento com erro
        assertTrue(characterScreenModel.characters.value.isEmpty())
    }
    
    @Test
    fun `applyFilters should update filtered characters flow`(): Unit = runTest {
        // Mock do repositório
        val expectedCharacters = listOf(
            Character(
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
                     ), Character(
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
                                       )
        coEvery {
            repository.getFilteredCharacters(
                any(), any(), any()
                                            )
        } returns createMockCharacterResponse(expectedCharacters)
        
        // Chamar a função
        characterScreenModel.applyFilters("name", "status")
        
        // Verificar se o flow de personagens filtrados foi atualizado corretamente
        characterScreenModel.characters.collect { characters ->
            assertEquals(expectedCharacters, characters)
        }
    }
    
    @Test
    fun `applyFilters with exception should update error flow`(): Unit = runBlocking {
        // Mock do repositório com uma exceção
        coEvery {
            repository.getFilteredCharacters(
                any(), any(), any()
                                            )
        } throws Exception("Error loading filtered characters")
        
        // Chamar a função
        characterScreenModel.applyFilters("name", "status")
        
        // Verificar se o flow de erro foi atualizado corretamente
        characterScreenModel.error.collect { error ->
            assertEquals(
                "Error loading filtered characters: Error loading filtered characters", error
                        )
        }
    }
}