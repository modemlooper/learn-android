package com.palmersquare.myapplication.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.palmersquare.myapplication.ui.theme.TopBarColors

data class SettingsData(
    val email: String = "",
    val password: String = "",
    val notificationsEnabled: Boolean = false,
    val emailsEnabled: Boolean = false
)

class SettingsViewModel : ViewModel() {
    var uiState by mutableStateOf(SettingsData())
        private set

    fun onSettingsChange(newSettings: SettingsData) {
        uiState = newSettings
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    settingsViewModel: SettingsViewModel = viewModel(),
) {
    val settingsData = settingsViewModel.uiState
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState() // Create a scroll state

    Scaffold(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            focusManager.clearFocus()
        },
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopBarColors.surface(),
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top
            ) {

                // Text
                ListItem(
                    headlineContent = {},
                    supportingContent = {
                        Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit.")

                    }
                )

                HorizontalDivider()

                // Email text field
                ListItem(
                    headlineContent = { Text("Email") },
                    supportingContent = {
                        OutlinedTextField(
                            // Read the value from the ViewModel's state.
                            value = settingsData.email,
                            // Update the state by calling the ViewModel's function.
                            onValueChange = { newEmail ->
                                settingsViewModel.onSettingsChange(settingsData.copy(email = newEmail))
                            },
                            placeholder = { Text("e.g., user@example.com") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }
                )

                // Password text filed
                ListItem(
                    headlineContent = { Text("Password") },
                    supportingContent = {
                        OutlinedTextField(
                            // Read the value from the ViewModel's state.
                            value = settingsData.password,
                            // Update the state by calling the ViewModel's function.
                            onValueChange = { newPassword ->
                                settingsViewModel.onSettingsChange(settingsData.copy(password = newPassword))
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }
                )

                // Switch with text
                ListItem(
                    headlineContent = { Text("Notifications") },
                    trailingContent = {
                        Switch(
                            checked = settingsData.notificationsEnabled,
                            onCheckedChange = { newCheckedState ->
                                settingsViewModel.onSettingsChange(settingsData.copy(notificationsEnabled = newCheckedState))
                                focusManager.clearFocus()
                            },

                        )
                    }
                )

                // Checkbox with text
                ListItem(
                    headlineContent = { Text("Receive Emails") },
                    trailingContent = {
                        Checkbox(
                            checked = settingsData.emailsEnabled,
                            onCheckedChange = { newCheckedState ->
                                settingsViewModel.onSettingsChange(settingsData.copy(emailsEnabled = newCheckedState))
                                focusManager.clearFocus()
                            }
                        )
                    }
                )

                // Card with title, subheading, and text
                ListItem(
                    headlineContent = {},
                    supportingContent = {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Title",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Subheading",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                )

                // Horizontal Multi-Browse Carousel
                val items = remember {
                    listOf(
                        "Item 1",
                        "Item 2",
                        "Item 3",
                        "Item 4",
                        "Item 5",
                        "Item 6",
                        "Item 7",
                        "Item 8",
                    )
                }

                HorizontalMultiBrowseCarousel(
                    state = rememberCarouselState { items.count() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 16.dp, bottom = 16.dp),
                    preferredItemWidth = 186.dp,
                    itemSpacing = 8.dp,
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) { i ->
                    val item = items[i]
                    Card(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(item)
                        }
                    }
                }

                // Chips
                ListItem(
                    headlineContent = {},
                    supportingContent = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            AssistChip(
                                onClick = { Log.d("Assist chip", "hello world") },
                                label = { Text("Assist chip") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Settings,
                                        contentDescription = "Localized description",
                                        Modifier.size(AssistChipDefaults.IconSize)
                                    )
                                }
                            )

                            var selected by remember { mutableStateOf(true) }

                            FilterChip(
                                onClick = {},
                                label = {
                                    Text("Filter chip")
                                },
                                selected = selected,
                                leadingIcon = if (selected) {
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = "Done icon",
                                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                                        )
                                    }
                                } else {
                                    null
                                },
                            )

                            InputChip(
                                onClick = {},
                                label = { Text("Hello") },
                                selected = true,
                                avatar = {
                                    Icon(
                                        Icons.Filled.Person,
                                        contentDescription = "Localized description",
                                        Modifier.size(InputChipDefaults.AvatarSize)
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Localized description",
                                        Modifier.size(InputChipDefaults.AvatarSize)
                                    )
                                },
                            )

                            SuggestionChip(
                                onClick = { Log.d("Suggestion chip", "hello world") },
                                label = { Text("Suggestion chip") }
                            )
                        }

                    }
                )


            }
        }
    }
}

