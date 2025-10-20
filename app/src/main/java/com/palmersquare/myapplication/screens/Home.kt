package com.palmersquare.myapplication.screens

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.palmersquare.myapplication.Screen
import com.palmersquare.myapplication.ui.theme.TopBarColors
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    homeBackStack: NavBackStack<NavKey>
) {
    // 1. State for managing the bottom sheet's visibility and behavior
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showPopupMenu by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                colors = TopBarColors.surface(),
                actions = {
                    Box {
                        IconButton(onClick = { showPopupMenu = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Open Options",
                                tint = Color.White
                            )
                        }
                        DropdownMenu(
                            expanded = showPopupMenu,
                            onDismissRequest = { showPopupMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Open Sheet") },
                                onClick = {
                                    // TODO: Handle option 1 click
                                    showPopupMenu = false
                                    showBottomSheet = true
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Go to Detail") },
                                onClick = {
                                    // TODO: Handle option 2 click
                                    showPopupMenu = false
                                    homeBackStack.add(Screen.Detail(UUID.randomUUID().toString()))
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(
                    innerPadding
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column {
                Button(onClick = {
                    homeBackStack.add(Screen.Detail(UUID.randomUUID().toString()))
                }) {
                    Text(text = "Go to Detail")
                }

                Button(onClick = {
                    showBottomSheet = true
                }) {
                    Text(text = "Open Sheet")
                }
            }
        }
    }

    if (showBottomSheet) {

        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            // HACK to force status bar to have light icons
            val view = LocalView.current
            (view.parent as? DialogWindowProvider)?.window?.let { window ->
                SideEffect {
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                }
            }

            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("Click to close")
                }
            }
        }
    }

}