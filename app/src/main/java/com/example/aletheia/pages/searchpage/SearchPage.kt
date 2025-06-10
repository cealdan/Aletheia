package com.example.aletheia.pages.searchpage

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.compose.foundation.clickable
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.platform.LocalFocusManager


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import com.example.aletheia.AletheiaTheme
import com.example.aletheia.R
import com.example.aletheia.ThemeViewModel
import com.example.aletheia.viewmodels.GlobalViewModel


@Composable
fun SearchPage(globalViewModel: GlobalViewModel, themeViewModel: ThemeViewModel, username: String) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)


    var searchQuery by remember { mutableStateOf("") }
    val modelList = listOf("Pythia", "Qwen", "ChatGPT", "Deepseek", "Llama", "Mistral")
    val filteredModels = modelList.filter { it.contains(searchQuery, ignoreCase = true) }

    val focusmanager = LocalFocusManager.current
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()


    LaunchedEffect(Unit) {
        globalViewModel.setWhichPage("searchpage")
    }

    AletheiaTheme(themeViewModel, darkTheme = isDarkTheme) {
        Column(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusmanager.clearFocus()
                        }
                    )
                }
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.rechercher),
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleLarge
            )

            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,

                    ),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(stringResource(id = R.string.nom_modele), style = MaterialTheme.typography.bodyMedium) }, //ou nom du user? ou autre, ou rien
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            LazyColumn {
                items(filteredModels) { model ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { /* Action au clic */ },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(
                            text = model,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}