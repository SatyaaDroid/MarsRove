package com.app.marsrover.roverPhotos

import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import com.app.marsrover.domain.use_cases.GetMarsRoverPhotoUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RoverPhotoUseCaseTest {

    private lateinit var getMarsRoverPhotoRepository: GetMarsRoverPhotoRepository
    private lateinit var getMarsRoverPhotoUseCase: GetMarsRoverPhotoUseCase
    private val roverPhotoUiModel = mock<List<RoverPhotoUiModel>>()
    private val roverName = "Perseverance"
    private val sol = "0"

    @Before
    fun setUp() {
        getMarsRoverPhotoRepository = mock()
        getMarsRoverPhotoUseCase = GetMarsRoverPhotoUseCase(getMarsRoverPhotoRepository)
    }

    @Test
    fun returnSuccessFromRoverPhotoUseCase() = runTest {
        `when`(getMarsRoverPhotoRepository.getMarsRoverPhotos(roverName, sol)).thenReturn(
            roverPhotoUiModel
        )
        getMarsRoverPhotoUseCase.invoke(roverName, sol).onEach {
            Assert.assertEquals(roverPhotoUiModel, it.data)
        }
    }

    @Test
    fun returnErrorFromRoverPhotoUseCase() = runTest {
        `when`(getMarsRoverPhotoRepository.getMarsRoverPhotos(roverName, sol)).thenThrow(
            RuntimeException("Something went wrong")
        )

        getMarsRoverPhotoUseCase.invoke(roverName, sol).onEach {
            Assert.assertEquals("Something went wrong", it.message)
        }

    }


    @Test
    fun returnSuccessWhenAddDataInDB() = runTest {
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One",
            isSaved = true
        )

        `when`(getMarsRoverPhotoRepository.savePhoto(roverPhotoUiModel)).thenAnswer { Unit }


        getMarsRoverPhotoUseCase.savePhoto(roverPhotoUiModel)

        Mockito.verify(getMarsRoverPhotoRepository).savePhoto(roverPhotoUiModel)

    }

    @Test
    fun returnSuccessWhenRemoveInDB() = runTest {
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One",
            isSaved = true
        )

        `when`(getMarsRoverPhotoRepository.removePhoto(roverPhotoUiModel)).thenAnswer { Unit }


        getMarsRoverPhotoUseCase.removePhoto(roverPhotoUiModel)

        Mockito.verify(getMarsRoverPhotoRepository).removePhoto(roverPhotoUiModel)

    }

    @Test
    fun returnSuccessWhenAllDataRetriveFromDB() = runTest {

        val marsRoverSavedLocalModelList = listOf(
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


        `when`(getMarsRoverPhotoRepository.getAllSavedData()).thenReturn(
            flowOf(marsRoverSavedLocalModelList)
        )


        val result = getMarsRoverPhotoUseCase.getAllSavedData().first()

        val exceptedResult = listOf(
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

        Assert.assertEquals(exceptedResult, result)

    }

}