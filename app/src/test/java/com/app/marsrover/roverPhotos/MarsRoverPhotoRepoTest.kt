package com.app.marsrover.roverPhotos

import com.app.marsrover.MainCoroutineRule
import com.app.marsrover.data.common.RoverPhotoUiModel
import com.app.marsrover.data.common.RoverPhotoUiState
import com.app.marsrover.data.network.Api
import com.app.marsrover.db.MarsRoverSavedLocalModel
import com.app.marsrover.db.MarsRoverSavedPhotoDao
import com.app.marsrover.data.model.CameraRemoteModel
import com.app.marsrover.data.model.PhotoRemoteModel
import com.app.marsrover.data.model.RoverPhotoRemoteModel
import com.app.marsrover.data.model.RoverRemoteModel
import com.app.marsrover.data.repository.GetMarsRoverPhotoRepositoryImpl
import com.app.marsrover.data.repository.MarsRoverPhotoRepo
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

class MarsRoverPhotoRepoTest {

    private val marsRoverPhotoService = mockkClass(Api::class)
    private val marsRoverSavedPhotoDao = mockkClass(MarsRoverSavedPhotoDao::class)

    private lateinit var marsRoverPhotoRepo : GetMarsRoverPhotoRepository

    @get:Rule
    val coroutineRule = MainCoroutineRule()


    @Before
    fun setUp(){
        marsRoverPhotoRepo = GetMarsRoverPhotoRepositoryImpl(marsRoverPhotoService)

    }

    @Test
    fun `should emit success when service and dao return valid data`() =
        runTest(coroutineRule.testDispatcher) {
            //Given
            val roverPhotoRemoteModel = RoverPhotoRemoteModel(
                photos = listOf(
                    PhotoRemoteModel(
                        camera = CameraRemoteModel(
                            fullName = "Camera One",
                            id = 1,
                            name = "Camera 1",
                            roverID = 1
                        ),
                        earthDate = "2022-07-02",
                        id = 2,
                        imgSrc = "https://example.com/photo1",
                        rover = RoverRemoteModel(
                            id = 5,
                            landingDate = "2021-02-18",
                            launchDate = "2020-07-30",
                            name = "Perseverance",
                            status = "active"
                        ),
                        sol = 20
                    ),
                    PhotoRemoteModel(
                        camera = CameraRemoteModel(
                            fullName = "Camera Two",
                            id = 3,
                            name = "Camera 2",
                            roverID = 1
                        ),
                        earthDate = "2022-07-02",
                        id = 4,
                        imgSrc = "https://example.com/photo2",
                        rover = RoverRemoteModel(
                            id = 5,
                            landingDate = "2021-02-18",
                            launchDate = "2020-07-30",
                            name = "Perseverance",
                            status = "active"
                        ),
                        sol = 20
                    )
                )
            )
            coEvery {
                marsRoverPhotoService.getMarsRoverPhotos("perseverance", "0")
            } returns roverPhotoRemoteModel

            coEvery {
                marsRoverSavedPhotoDao.allSavedIds("0", "perseverance")
            } returns flowOf(listOf(2))

            //When
            val result = marsRoverPhotoRepo.getMarsRoverPhotos("perseverance", "0").toList()

            //Then
            val expectedResult = RoverPhotoUiState.Success(
                roverPhotoUiModelList = listOf(
                    RoverPhotoUiModel(
                        id = 2,
                        roverName = "Perseverance",
                        imgSrc = "https://example.com/photo1",
                        sol = "20",
                        earthDate = "2022-07-02",
                        cameraFullName = "Camera One",
                        isSaved = true
                    ),
                    RoverPhotoUiModel(
                        id = 4,
                        roverName = "Perseverance",
                        imgSrc = "https://example.com/photo2",
                        sol = "20",
                        earthDate = "2022-07-02",
                        cameraFullName = "Camera Two",
                        isSaved = false
                    )
                )
            )
            assertEquals(1, result.size)
            assertEquals(expectedResult, result[0])
        }

    @Test
    fun `should emit error when service throw exception`() = runTest(coroutineRule.testDispatcher) {
        //Given
        coEvery {
            marsRoverPhotoService.getMarsRoverPhotos("perseverance", "0")
        } throws TimeoutException()

        coEvery {
            marsRoverSavedPhotoDao.allSavedIds("0", "perseverance")
        } returns flowOf(listOf(2))

        //When
        val result = marsRoverPhotoRepo.getMarsRoverPhotos("perseverance", "0").toList()

        //Then
        assertEquals(1, result.size)
        assertEquals(RoverPhotoUiState.Error, result[0])
    }

    @Test
    fun `should call insert when saved photo is called`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One",
            isSaved = true
        )
        val marsRoverSavedLocalModel = MarsRoverSavedLocalModel(
            roverPhotoId = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One"
        )
        coEvery {
            marsRoverSavedPhotoDao.insert(marsRoverSavedLocalModel)
        } returns Unit

        //When
        val marsRoverPhotoRepo = MarsRoverPhotoRepo(marsRoverPhotoService, marsRoverSavedPhotoDao)
        marsRoverPhotoRepo.savePhoto(roverPhotoUiModel)

        //Then
        coVerify { marsRoverSavedPhotoDao.insert(marsRoverSavedLocalModel) }
    }

    @Test
    fun `should call delete when remove photo is called`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One",
            isSaved = true
        )
        val marsRoverSavedLocalModel = MarsRoverSavedLocalModel(
            roverPhotoId = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One"
        )
        coEvery {
            marsRoverSavedPhotoDao.delete(marsRoverSavedLocalModel)
        } returns Unit

        //When
        val marsRoverPhotoRepo = MarsRoverPhotoRepo(marsRoverPhotoService, marsRoverSavedPhotoDao)
        marsRoverPhotoRepo.removePhoto(roverPhotoUiModel)

        //Then
        coVerify { marsRoverSavedPhotoDao.delete(marsRoverSavedLocalModel) }
    }

    @Test
    fun `should emit ui model when all data are retrieved`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val marsRoverSavedLocalModelList = listOf(
            MarsRoverSavedLocalModel(
                roverPhotoId = 2,
                roverName = "Perseverance",
                imgSrc = "https://example.com/photo1",
                sol = "20",
                earthDate = "2022-07-02",
                cameraFullName = "Camera One"
            ),
            MarsRoverSavedLocalModel(
                roverPhotoId = 4,
                roverName = "Perseverance",
                imgSrc = "https://example.com/photo2",
                sol = "20",
                earthDate = "2022-07-02",
                cameraFullName = "Camera Two"
            )
        )
        coEvery {
            marsRoverSavedPhotoDao.getAllSaved()
        } returns flowOf(marsRoverSavedLocalModelList)

        //When
        val marsRoverPhotoRepo = MarsRoverPhotoRepo(marsRoverPhotoService, marsRoverSavedPhotoDao)
        val result = marsRoverPhotoRepo.getAllSaved().first()

        //Then
        val exceptedResult = RoverPhotoUiState.Success(
            roverPhotoUiModelList = listOf(
                RoverPhotoUiModel(
                    id = 2,
                    roverName = "Perseverance",
                    imgSrc = "https://example.com/photo1",
                    sol = "20",
                    earthDate = "2022-07-02",
                    cameraFullName = "Camera One",
                    isSaved = true
                ),
                RoverPhotoUiModel(
                    id = 4,
                    roverName = "Perseverance",
                    imgSrc = "https://example.com/photo2",
                    sol = "20",
                    earthDate = "2022-07-02",
                    cameraFullName = "Camera Two",
                    isSaved = true
                )
            )
        )
        assertEquals(exceptedResult, result)
    }


}