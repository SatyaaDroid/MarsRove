package com.app.marsrover.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.marsrover.presentation.navigation.Destination.Home
import com.app.marsrover.presentation.navigation.Destination.Manifest
import com.app.marsrover.presentation.navigation.Destination.Photo
import com.app.marsrover.presentation.navigation.Destination.Saved
import com.app.marsrover.presentation.ui.theme.MarsroverTheme
import com.app.marsrover.presentation.marsrovers.screens.ManifestScreen
import com.app.marsrover.presentation.marsrovers.screens.PhotoListSavedScreen
import com.app.marsrover.presentation.marsrovers.screens.PhotoScreen
import com.app.marsrover.presentation.marsrovers.screens.RoverList

@Composable
fun NavCompose() {

    val items = listOf(
        Screen.Home,
        Screen.Saved
    )
    val navController = rememberNavController()
    val actions = remember(navController) { Action(navController) }

    MarsroverTheme {

        Scaffold(bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.drawableId),
                                contentDescription = stringResource(id = screen.resourceId)
                            )
                        },
                        label = {
                            Text(text = stringResource(id = screen.resourceId))
                        },
                        selected = currentDestination?.hierarchy?.any {
                            if (it.route?.contains("manifest") == true || it.route?.contains("photo") == true) {
                                screen.route == "home"
                            } else {
                                it.route == screen.route
                            }
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                }

            }


        }


        ) { innerpadding ->

            val modifier = Modifier.padding(innerpadding)

            NavHost(navController = navController, startDestination = Home) {
                composable(Home) {
                    RoverList(
                        modifier = Modifier.padding(innerpadding)
                    ) { roverName ->
                        actions.manifest(roverName)
                    }
                }
                composable(Manifest) { backStackEntry ->
                    ManifestScreen(
                        modifier = Modifier.padding(innerpadding),
                        roverName = backStackEntry.arguments?.getString("roverName"),
                        roverManifestViewModel = hiltViewModel(),
                        onClick = { roverName, sol ->
                            actions.Photo(roverName, sol)
                        }
                    )
                }
                composable(Photo) { backStackEntry ->
                    PhotoScreen(
                        modifier = Modifier.padding(innerpadding),
                        roverName = backStackEntry.arguments?.getString("roverName"),
                        sol = backStackEntry.arguments?.getString("sol"),
                        roverPhotosViewModel = hiltViewModel()
                    )
                }
                composable(Saved) {
                    PhotoListSavedScreen(
                        modifier = modifier,
                        roverSavedViewModel = hiltViewModel()
                    )
                }
            }
        }
    }
}