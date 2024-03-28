package com.app.marsrover.roverManifest

import androidx.lifecycle.SavedStateHandle
import com.app.marsrover.MainCoroutineRule
import com.app.marsrover.data.common.Resource
import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.use_cases.GetMarsRoverManifestUseCase
import com.app.marsrover.presentation.viewmodel.manifestlist.RoverManifestViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RoverManifestViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getMarsRoverManifestUseCase: GetMarsRoverManifestUseCase = mockk()
    private lateinit var roverManifestViewModel: RoverManifestViewModel
    private val roverManifestUiModel: RoverManifestUiModel? = mockk()


    @Before
    fun setUp() {
        val savedStateHandle = SavedStateHandle()
        savedStateHandle.set("roverName", "perseverance")
        roverManifestViewModel = RoverManifestViewModel(
            getMarsRoverManifestUseCase,
            savedStateHandle,
            mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `validate user will see Progressbar Init`() = runTest {

        //Given
        coEvery {
            getMarsRoverManifestUseCase.invoke("perseverance")
        } returns flow {
            emit(Resource.Loading())
        }

        mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(true, roverManifestViewModel.roverManifestStateHolder.value.isLoading)

//        val result = getMarsRoverManifestUseCase.invoke("perseverance")

    }
}