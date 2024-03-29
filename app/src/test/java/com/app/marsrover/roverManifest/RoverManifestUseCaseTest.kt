package com.app.marsrover.roverManifest

import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.repository.GetMarsRoverManifestRepository
import com.app.marsrover.domain.use_cases.GetMarsRoverManifestUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RoverManifestUseCaseTest {

    private lateinit var getMarsRoverManifestRepository: GetMarsRoverManifestRepository
    private lateinit var getMarsRoverManifestUseCase: GetMarsRoverManifestUseCase
    private val roverManifestUiModel = mock<List<RoverManifestUiModel>>()
    private val roverName = "Perseverance"

    @Before
    fun setUp(){
        getMarsRoverManifestRepository = mock()
        getMarsRoverManifestUseCase = GetMarsRoverManifestUseCase(getMarsRoverManifestRepository)
    }

    @Test
    fun returnRoverManifestUseCaseSuccess() = runTest {
        `when`(getMarsRoverManifestRepository.getMarsRoverManifest(roverName)).thenReturn(
            roverManifestUiModel
        )
        getMarsRoverManifestUseCase.invoke(roverName).onEach {
            Assert.assertEquals(roverManifestUiModel,it.data)
        }
    }

    @Test
    fun returnErrorRoverManifestUseCaseSuccess()= runTest {
        `when`(getMarsRoverManifestRepository.getMarsRoverManifest(roverName)).thenThrow(
            RuntimeException("Something went wrong")
        )

        getMarsRoverManifestUseCase.invoke(roverName).onEach {
            Assert.assertEquals("Something went wrong",it.message)
        }

    }
}