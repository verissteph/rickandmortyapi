package com.stephanieverissimo.rickandmortyapi.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.stephanieverissimo.rickandmortyapi.data.model.Character
import com.stephanieverissimo.rickandmortyapi.presentation.viewModel.CharacterScreenModel

object CharacterListScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: CharacterScreenModel = getScreenModel()
        
        
        val characters by screenModel.characters.collectAsState(emptyList())
        val error by screenModel.error.collectAsState(null)
        val filteredCharacters by screenModel.filteredCharacters.collectAsState()
        val filtersAppliedRecently by screenModel.filtersAppliedRecently.collectAsState()
        
        val state by screenModel.state.collectAsState()
        
        
        
        LaunchedEffect(Unit) {
            screenModel.loadCharacters(1)
        }
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Characters") },
                    actions = {
                        IconButton(
                            onClick = {
                                navigator.push(
                                    CharacterFilterScreen(
                                        onFilterApplied = { name, status ->
                                            screenModel.applyFilters(name, status)
                                            
                                        }
                                    )
                                )
                            },
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Filter")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (state) {
                    CharacterScreenModel.State.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center)
                        )
                    }
                    
                    CharacterScreenModel.State.Error -> {
                        Text(
                            text = error!!,
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                    
                    else -> {
                        if (filteredCharacters.isNotEmpty() || filtersAppliedRecently) {
                            CharacterList(characters = filteredCharacters) { character ->
                                navigator.push(CharacterDetailsScreen(character = character))
                            }
                        } else {
                            CharacterList(characters) { character ->
                                navigator.push(CharacterDetailsScreen(character = character))
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Composable
    fun CharacterList(characters: List<Character>, onItemClick: (Character) -> Unit) {
        val screenModel: CharacterScreenModel = getScreenModel()
        val lazyListState = rememberLazyListState()
        
        
        
        println("CharacterList invoked. Number of characters: ${characters.size}")
        LazyColumn(state = lazyListState) {
            itemsIndexed(characters) { index, character ->
                CharacterListItem(character = character, onItemClick = onItemClick)
                
                LaunchedEffect(lazyListState.firstVisibleItemIndex) {
                    if (lazyListState.firstVisibleItemIndex == 0) {
                        screenModel.loadPreviousCharacters()
                    }
                }
                
                if (index == characters.size - 1) {
                    screenModel.loadMoreCharacters()
                }
            }
        }
    }
    
    @Composable
    fun CharacterListItem(character: Character, onItemClick: (Character) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(character) }
                .padding(8.dp)
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = character.image)
                    .apply {
                        crossfade(true)
                    }.build()
            )
            Image(
                painter = painter,
                contentDescription = "Image character",
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = character.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}


