package com.app.marsrover

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.presentation.ui.theme.MarsroverTheme
import com.app.marsrover.presentation.screens.ManifestList
import com.app.marsrover.presentation.screens.PhotoList
import com.app.marsrover.presentation.screens.RoverList

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRoverList() {
        //When

        composeTestRule.setContent {
            MarsroverTheme {
                RoverList(modifier = Modifier, onClick = {})
            }
        }

        //Then
        composeTestRule.onNodeWithText("Perseverance").assertIsDisplayed()
        composeTestRule.onNodeWithText("Curiosity").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Landing date: 18 February 2021").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Distance traveled: 12.56km").assertIsDisplayed()
    }

    @Test
    fun testManifestList() {
        //Given
        val roverManifestUiModelList = listOf(
            RoverManifestUiModel(
                sol = "1",
                earthDate = "2021-02-19",
                photoNumber = "201"
            ),
            RoverManifestUiModel(
                sol = "0",
                earthDate = "2021-02-18",
                photoNumber = "54"
            )
        )

        //When
        composeTestRule.setContent {
            MarsroverTheme {
                ManifestList(
                    modifier = Modifier,
                    roverManifestUiModelList = roverManifestUiModelList,
                    roverName = "",
                    onClick = { _, _ -> }
                )
            }
        }

        //Then
        composeTestRule.onNodeWithText("Sol: 0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Earth date: 2021-02-18").assertIsDisplayed()
        composeTestRule.onNodeWithText("Number of photo: 54")
        composeTestRule.onNodeWithText("Earth date: 2021-02-19").assertIsDisplayed()
        composeTestRule.onNodeWithText("Number of photo: 201")
    }

    @Test
    fun testPhotoList() {
        //Given
        val roverPhotoUiModelList = listOf(
            RoverPhotoUiModel(
                id = 1,
                roverName = "perseverance",
                imgSrc = "https://example.com/photo1",
                sol = "0",
                earthDate = "2022-03-10",
                cameraFullName = "Camera One",
                isSaved = true
            ),
            RoverPhotoUiModel(
                id = 2,
                roverName = "perseverance",
                imgSrc = "https://example.com/photo2",
                sol = "1",
                earthDate = "2022-03-11",
                cameraFullName = "Camera Two",
                isSaved = false
            )
        )

        //When
        composeTestRule.setContent {
            MarsroverTheme {
                PhotoList(
                    modifier = Modifier,
                    roverPhotoUiModelList = roverPhotoUiModelList,
                    onClick = {})
            }
        }

        //Then
        composeTestRule.onAllNodesWithContentDescription("save icon").assertCountEquals(2)
        composeTestRule.onNodeWithText("Sol: 0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Earth date: 2022-03-10").assertIsDisplayed()
        composeTestRule.onNodeWithText("Camera One").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sol: 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Earth date: 2022-03-11").assertIsDisplayed()
        composeTestRule.onNodeWithText("Camera Two").assertIsDisplayed()
    }
}