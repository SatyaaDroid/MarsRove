package com.app.marsrover.roverManifest

import com.app.marsrover.data.common.RoverManifestUiState
import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.repository.GetMarsRoverManifestRepository
import com.app.marsrover.domain.use_cases.GetMarsRoverManifestUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException

class MarsRoverManifestUseCaseTest {

    private lateinit var getMarsRoverManifestRepository: GetMarsRoverManifestRepository
    private lateinit var getMarsRoverManifestUseCase: GetMarsRoverManifestUseCase
    private val roverManifestUiModel = mockk<RoverManifestUiModel>()

    @Before
    fun setUp() {
        getMarsRoverManifestRepository = mockk()
        getMarsRoverManifestUseCase = GetMarsRoverManifestUseCase(getMarsRoverManifestRepository)
    }

    @Test
    fun `return RoverManifest UseCase In Success`() = runTest {

        //Given
        coEvery {
            getMarsRoverManifestRepository.getMarsRoverManifest("perseverance")
        } returns listOf(roverManifestUiModel)


        //When
        val result = getMarsRoverManifestUseCase.invoke("perseverance").toList()

        //Then
        assertEquals(roverManifestUiModel, result[1].data)

    }

    @Test
    fun `return Error RoverManifest UseCase In Success`() = runTest {

        //Given
        coEvery {
            getMarsRoverManifestRepository.getMarsRoverManifest("perseverance")
        } throws RuntimeException("Something went wrong")

        //When
        val result =  getMarsRoverManifestUseCase.invoke("perseverance").toList()

        //Then
//        assertEquals(1, result.size)
        assertEquals("Something went wrong", result[1].message)

    }

}