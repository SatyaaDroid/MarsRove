package com.app.marsrover.roverManifest

import androidx.lifecycle.SavedStateHandle
import com.app.marsrover.MainCoroutineRule
import com.app.marsrover.core.common.Resource
import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.use_cases.GetMarsRoverManifestUseCase
import com.app.marsrover.presentation.marsrovers.viewmodel.RoverManifestViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RoverManifestViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getMarsRoverManifestUseCase: GetMarsRoverManifestUseCase = mock()
    private lateinit var roverManifestViewModel: RoverManifestViewModel
    private val roverManifestUiModel: List<RoverManifestUiModel>? = mock()
    private val roverName = "Perseverance"

    @Before
    fun setUp() {
        val savedStateHandle = SavedStateHandle()
        savedStateHandle.set("roverName", roverName)
        roverManifestViewModel =
            RoverManifestViewModel(
                getMarsRoverManifestUseCase,
                savedStateHandle,
                mainCoroutineRule.testDispatcher
            )
    }

    @Test
    fun returnSuccessWhenUserSeeProgressBarInit() = runTest {

        `when`(getMarsRoverManifestUseCase.invoke(roverName)).thenReturn(
            flow {
                emit(Resource.Loading())
            }
        )
        mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(true, roverManifestViewModel.roverManifestStateHolder.value.isLoading)
    }


    @Test
    fun returnSuccessWhenStateStoreData() = runTest {

        `when`(getMarsRoverManifestUseCase.invoke(roverName)).thenReturn(
            flow {
                emit(Resource.Success(roverManifestUiModel))
            }
        )
        mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(
            roverManifestUiModel,
            roverManifestViewModel.roverManifestStateHolder.value.data
        )
    }

    @Test
    fun returnErrorInErrorCase() = runTest {
        `when`(getMarsRoverManifestUseCase.invoke(roverName)).thenReturn(
            flow {
                emit(Resource.Error("Something went wrong"))
            }
        )
        mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(
            "Something went wrong",
            roverManifestViewModel.roverManifestStateHolder.value.error
        )
    }


}