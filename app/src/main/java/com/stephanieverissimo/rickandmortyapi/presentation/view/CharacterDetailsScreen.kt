package com.stephanieverissimo.rickandmortyapi.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.stephanieverissimo.rickandmortyapi.data.model.Character

data class CharacterDetailsScreen(val character: Character) : Screen {
	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		val navigator = LocalNavigator.currentOrThrow
		Scaffold(
				topBar = {
					TopAppBar(
							title = { Text(text = "Details") },
							navigationIcon = {
								IconButton(onClick = { navigator.pop() }) {
									Icon(Icons.Default.ArrowBack, contentDescription = "Back")
								}
							}
					         )
				},
				content = { innerPadding ->
					Box(
							modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .verticalScroll(rememberScrollState()),
					   ) {
						Column(
								modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
						      ) {
							val painter =
								rememberAsyncImagePainter(
										ImageRequest
											.Builder(LocalContext.current)
											.data(
													data = character.image
											     )
											.apply(block = fun ImageRequest.Builder.() {
												crossfade(true)
											}).build(),
								                         )
							Image(
									painter = painter,
									contentDescription = "Image character",
									modifier = Modifier
                                        .size(
                                                400.dp
                                             )
                                        .background(MaterialTheme.colorScheme.primary)
                                        .align(Alignment.CenterHorizontally),
									contentScale = ContentScale.Crop,
							     )
							
							Text(
									text = character.name,
									fontWeight = FontWeight.Bold,
									fontSize =
									24.sp,
									modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(8.dp),
							    )
							
							Text(
									text = "Species: ${character.species}",
									fontWeight = FontWeight.Bold,
									fontSize = 20.sp,
									modifier = Modifier.padding(8.dp),
							    )
							
							Text(
									text = "Gender: ${character.gender}",
									fontWeight = FontWeight.Bold,
									fontSize = 20.sp,
									modifier = Modifier.padding(8.dp),
							    )
							
							Text(
									text = "Status: ${character.status}",
									fontWeight = FontWeight.Bold,
									fontSize = 20.sp,
									modifier = Modifier.padding(8.dp),
							    )
							
							Text(
									text = "Type: ${character.type}",
									fontWeight = FontWeight.Bold,
									fontSize = 20.sp,
									modifier = Modifier.padding(8.dp),
							    )
						}
					}
				}
		        )
	}
}
