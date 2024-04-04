package com.app.marsrover.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import com.app.marsrover.R
import com.app.marsrover.presentation.navigation.Destination.Home


object Destination {
    const val Home = "home"
    const val Manifest = "manifest/{roverName}"
    const val Photo = "photo/{roverName}?sol={sol}"
    const val Saved = "saved"

}

class Action(navController: NavController) {
    val home: () -> Unit = { navController.navigate(Home) }
    val manifest: (roverName: String) -> Unit = { rovername ->
        navController.navigate("manifest/${rovername}")
    }
    val Photo: (roverName: String, sol: String) -> Unit = { roverName, sol ->
        navController.navigate("photo/${roverName}?sol=${sol}")

    }
}

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val drawableId: Int
) {
    data object Home : Screen("home", R.string.rover, R.drawable.ic_mars_rover)

    data object Saved : Screen("Saved", R.string.saved, R.drawable.ic_save)

}