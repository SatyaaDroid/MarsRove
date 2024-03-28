package com.app.marsrover.domain.use_cases

import com.app.marsrover.data.common.Resource

import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.repository.GetMarsRoverManifestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMarsRoverManifestUseCase @Inject constructor(
    private val getMarsRoverManifestRepository: GetMarsRoverManifestRepository,
) {


    operator fun invoke(roverName: String): Flow<Resource<List<RoverManifestUiModel>>> = flow {
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success( data = getMarsRoverManifestRepository.getMarsRoverManifest(roverName)))
        } catch (throwable: Throwable) {
            emit(Resource.Error(message = throwable.message.toString()))
        }
    }


}