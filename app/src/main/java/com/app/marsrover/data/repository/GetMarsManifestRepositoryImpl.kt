package com.app.marsrover.data.repository


import com.app.marsrover.data.mappers.toDomain
import com.app.marsrover.data.network.Api
import com.app.marsrover.domain.model.RoverManifestUiModel
import com.app.marsrover.domain.repository.GetMarsRoverManifestRepository
import javax.inject.Inject

class GetMarsManifestRepositoryImpl @Inject constructor(
    private val api: Api
) : GetMarsRoverManifestRepository {
    override suspend fun getMarsRoverManifest(roverName: String): List<RoverManifestUiModel> {
//        val roverManifestRemoteModel = api.getMarsRoverManifest(roverName)
        return api.getMarsRoverManifest(roverName).photoManifestRemoteModel.photos!!.toDomain()
    }
}
