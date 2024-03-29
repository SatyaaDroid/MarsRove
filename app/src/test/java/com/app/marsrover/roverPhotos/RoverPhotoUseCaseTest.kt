package com.app.marsrover.roverPhotos

import com.app.marsrover.domain.model.RoverPhotoUiModel
import com.app.marsrover.domain.repository.GetMarsRoverPhotoRepository
import com.app.marsrover.domain.use_cases.GetMarsRoverPhotoUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
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
    fun returnErrorFromRoverPhotoUseCase()= runTest {
        `when`(getMarsRoverPhotoRepository.getMarsRoverPhotos(roverName, sol)).thenThrow(
            RuntimeException("Something went wrong")
        )

        getMarsRoverPhotoUseCase.invoke(roverName, sol).onEach {
            Assert.assertEquals("Something went wrong",it.message)
        }

    }


}