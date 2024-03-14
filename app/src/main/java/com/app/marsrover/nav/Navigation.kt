package com.app.marsrover.nav

import android.provider.ContactsContract.Contacts.Photo
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import com.app.marsrover.R
import com.app.marsrover.nav.Destination.Home
import com.app.marsrover.nav.Destination.Manifest


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
    object Home : Screen("home", R.string.rover, R.drawable.ic_mars_rover)

    object Saved : Screen("Saved", R.string.saved, R.drawable.ic_save)

}