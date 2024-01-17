package com.stephanieverissimo.rickandmortyapi.presentation.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class CharacterFilterScreen(
        val onFilterApplied: (name: String?, status: String?) -> Unit,
                                ) : Screen {
	@Composable
	override fun Content() {
		val navigator = LocalNavigator.currentOrThrow
		
		var nameFilter by remember { mutableStateOf("") }
		var selectedStatusFilter by remember { mutableStateOf("") }
		
		val characterStatusList = listOf("Alive", "Dead", "Unknown")
		
		Column(
				modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
		      ) {
			TextField(
					value = nameFilter,
					onValueChange = { nameFilter = it },
					label = { Text("Filter by name") },
					modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
			         )
			
			Text("Filter by status", modifier = Modifier.padding(8.dp))
			Row(
					modifier =
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
			   ) {
				characterStatusList.forEach { status ->
					RadioButton(
							selected = selectedStatusFilter == status,
							onClick = { selectedStatusFilter = status },
							modifier = Modifier.padding(8.dp)
					           )
					Text(status, modifier = Modifier.padding(8.dp))
				}
			}
			
			Button(
					onClick = {
						onFilterApplied(nameFilter, selectedStatusFilter)
						navigator.pop()
					},
					modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
			      ) {
				Text("Filter")
			}
		}
	}
}
