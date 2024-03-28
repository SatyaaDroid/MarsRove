package com.app.marsrover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.marsrover.presentation.navigation.NavCompose
import com.app.marsrover.presentation.ui.theme.MarsroverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarsroverTheme {
                NavCompose()
            }
        }
    }
}
