package com.palmersquare.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.palmersquare.myapplication.screens.Detail
import com.palmersquare.myapplication.screens.Home
import com.palmersquare.myapplication.screens.Settings
import com.palmersquare.myapplication.ui.theme.MyApplicationTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.Transparent.hashCode()
            )
        )
        setContent {
            MyApplicationTheme {
                BasicNavigation()
            }
        }
    }
}

sealed class Screen: NavKey {
    @Serializable data object Home: Screen()
    @Serializable data object Settings: Screen()
    @Serializable data class Detail(val id: String): Screen()
}

// Represents the main tabs in your NavigationSuite
enum class MainTabs {
    HOME,
    SETTINGS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicNavigation() {

    // 1. Create a backstack for each tab.
    val homeBackStack = rememberNavBackStack(Screen.Home)
    val settingsBackStack = rememberNavBackStack(Screen.Settings)

    // 2. Create a state variable to track the currently selected tab.
    //    This is the "source of truth" for which tab is active.
    var selectedTab by rememberSaveable { mutableStateOf(MainTabs.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            // --- HOME TAB ---
            item(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                },
                label = { Text("Home") },
                // Select this tab if it's the active one.
                selected = selectedTab == MainTabs.HOME,
                onClick = {
                    // When clicked, make this tab the active one.
                    selectedTab = MainTabs.HOME
                }
            )

            // --- SETTINGS TAB ---
            item(
                icon = { Icon(Icons.Default.Settings, "Settings") },
                label = { Text("Settings") },
                // Select this tab if it's the active one.
                selected = selectedTab == MainTabs.SETTINGS,
                onClick = {
                    // When clicked, make this tab the active one.
                    selectedTab = MainTabs.SETTINGS
                }
            )
        }
    ) {

        when (selectedTab) {
            MainTabs.HOME -> {
                NavDisplay(
                    // Display the home backstack when the HOME tab is selected.
                    backStack = homeBackStack,
                    onBack = { homeBackStack.removeLastOrNull() },
                    entryProvider = entryProvider {

                        // --- Home Screen Entry ---
                        entry<Screen.Home> {
                            Home(homeBackStack = homeBackStack)
                        }

                        // --- Detail Screen Entry ---
                        // This screen is navigated to from Home.
                        entry<Screen.Detail> {
                            Detail(
                                id = it.id,
                                onBack = { homeBackStack.removeLastOrNull() }
                            )
                        }
                    },
                    // Optional: Add transitions for the home tab as well
                    transitionSpec = {
                        slideInHorizontally(initialOffsetX = { it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { -it })
                    },
                    popTransitionSpec = {
                        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                slideOutHorizontally(targetOffsetX = { it })
                    },
                )
            }

            MainTabs.SETTINGS -> {
                NavDisplay(
                    // Display the settings backstack when the SETTINGS tab is selected.
                    backStack = settingsBackStack,
                    onBack = { settingsBackStack.removeLastOrNull() },
                    entryProvider = entryProvider {

                        // --- Settings Screen Entry ---
                        entry<Screen.Settings> {
                            Settings()
                        }
                    }
                )
            }
        }
    }


}
