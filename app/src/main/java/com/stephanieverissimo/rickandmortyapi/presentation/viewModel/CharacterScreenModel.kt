package com.stephanieverissimo.rickandmortyapi.presentation.viewModel

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.stephanieverissimo.rickandmortyapi.data.model.Character
import com.stephanieverissimo.rickandmortyapi.data.model.FilterState
import com.stephanieverissimo.rickandmortyapi.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterScreenModel(private val repository: CharacterRepository) :
    StateScreenModel<CharacterScreenModel.State>(State.Init) {
    
    
    sealed class State {
        object Init : State()
        object Loading : State()
        object Error : State()
    }
    
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    private val _filteredCharacters = MutableStateFlow<List<Character>>(emptyList())
    val filteredCharacters: StateFlow<List<Character>> = _filteredCharacters
    
    private val _filterState = MutableStateFlow<FilterState?>(null)
    val filterState: StateFlow<FilterState?> = _filterState
    
    private val _filtersAppliedRecently = MutableStateFlow(false)
    val filtersAppliedRecently: StateFlow<Boolean> = _filtersAppliedRecently
    
    
    private var currentPage = 1
    private var filteredCurrentPage = 1
    
    init {
        loadCharacters(currentPage)
    }
    
    fun loadMoreCharacters() {
        loadCharacters(currentPage)
        loadFilteredCharacters()
    }
    
    fun loadCharacters(page: Int) {
        screenModelScope.launch {
            try {
                _isLoading.value = true
                val charactersResult = repository.getCharacters(page)
                if (page == 1) {
                    _characters.value = charactersResult.results
                } else {
                    _characters.value += charactersResult.results
                }
                currentPage = page + 1
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error loading characters: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun applyFilters(name: String?, status: String?) {
        currentPage = 1
        filteredCurrentPage = 1
        
        _filterState.value = FilterState(name, status)
        loadFilteredCharacters()
        _filtersAppliedRecently.value = true
    }
    
    private fun loadFilteredCharacters() {
        screenModelScope.launch {
            try {
                _isLoading.value = true
                val charactersResult = repository.getFilteredCharacters(
                    filteredCurrentPage,
                    _filterState.value?.name,
                    _filterState.value?.status
                )
                if (filteredCurrentPage == 1) {
                    _filteredCharacters.value = charactersResult.results
                } else {
                    _filteredCharacters.value += charactersResult.results
                }
                filteredCurrentPage += 1
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error loading filtered characters: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadPreviousCharacters() {
        if (currentPage > 1) {
            currentPage--
            loadCharacters(currentPage)
        }
    }
    
    
}
