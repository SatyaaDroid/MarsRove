package com.app.marsrover.roverManifest

import com.app.marsrover.MainCoroutineRule
import com.app.marsrover.data.common.RoverManifestUiModel
import com.app.marsrover.data.common.RoverManifestUiState
import com.app.marsrover.data.network.Api
import com.app.marsrover.data.model.ManifestPhotoRemoteModel
import com.app.marsrover.data.model.PhotoManifestRemoteModel
import com.app.marsrover.data.model.RoverManifestRemoteModel
import com.app.marsrover.data.repository.GetMarsManifestRepositoryImpl
import com.app.marsrover.domain.repository.GetMarsRoverManifestRepository
import io.mockk.coEvery
import io.mockk.mockkClass
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

class MarsRoverManifestRepoTest {

    private val marsRoverManifestService = mockkClass(Api::class)
    private lateinit var marsRoverManifestRepo: GetMarsRoverManifestRepository

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        marsRoverManifestRepo = GetMarsManifestRepositoryImpl(marsRoverManifestService)
    }


    @Test
    fun `should emit success when manifest service is successfull`() =
        runTest(coroutineRule.testDispatcher) {

            //Given
            val roverManifestRemoteModel = RoverManifestRemoteModel(
                photoManifestRemoteModel = PhotoManifestRemoteModel(
                    landingDate = "2021-02-18",
                    lunchDate = "2020-07-30",
                    maxDate = "2023-05-09",
                    maxSol = "799",
                    name = "Perserance",
                    photos = listOf(
                        ManifestPhotoRemoteModel(
                            cameras = listOf("camera1", "camera2"),
                            earthDate = "2021-02-18",
                            sol = 0,
                            totalPhotos = 54,
                        ),
                        ManifestPhotoRemoteModel(
                            cameras = listOf("camera2", "camera3"),
                            earthDate = "2021-02-19",
                            sol = 1,
                            totalPhotos = 201,
                        ),

                        ),
                    status = "active",
                    totalPhotos = 1250
                )
            )

            coEvery {
                marsRoverManifestService.getMarsRoverManifest("perseverance")
            } returns roverManifestRemoteModel

            //When
            val result = marsRoverManifestRepo.getMarsRoverManifest("perseverance").toList()

            val expectedResult = RoverManifestUiState.Success(
                listOf(
                    RoverManifestUiModel(
                        sol = "1",
                        earthDate = "2021-02-19",
                        photoNumber = "201"
                    ),
                    RoverManifestUiModel(
                        sol = "0",
                        earthDate = "2021-02-18",
                        photoNumber = "54"
                    ),
                )
            )

            assertEquals(1, result.size)
            assertEquals(expectedResult, result[0])
        }


    @Test
    fun `should emit error when manifest service throw timeout exception`() =
        runTest(coroutineRule.testDispatcher) {
            //Given
            coEvery {
                marsRoverManifestService.getMarsRoverManifest("perseverance")
            } throws TimeoutException()

            //When
            val result = marsRoverManifestRepo.getMarsRoverManifest("perseverance").toList()

            //Then
            assertEquals(1, result.size)
            assertEquals(RoverManifestUiState.Error, result[0])


        }
}