package com.app.marsrover.data

import com.app.marsrover.db.MarsRoverSavedPhotoDao
import com.app.marsrover.model.RoverPhotoUiModel
import com.app.marsrover.model.RoverPhotoUiState
import com.app.marsrover.model.toDbModel
import com.app.marsrover.model.toUiModel
import com.app.marsrover.service.MarsRoverPhotoService
import com.app.marsrover.service.mode.RoverPhotoRemoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarsRoverPhotoRepo @Inject constructor(
    private val marsRoverPhotoService: MarsRoverPhotoService,
    private val marsRoverSavedPhotoDao: MarsRoverSavedPhotoDao
) {


    private fun getAllRemoteMarsRoverPhotos(
        roverName: String, sol: String
    ): Flow<RoverPhotoRemoteModel?> = flow {
        try {
            val networkRequest = marsRoverPhotoService.getMarsRoverPhotos(
                roverName.lowercase(),
                sol.lowercase()
            )
            emit(networkRequest)

        } catch (thrwable: Throwable) {
            emit(null)
        }

    }


    fun getMarsRoverPhoto(
        roverName: String,
        sol: String
    ): Flow<RoverPhotoUiState> =
        marsRoverSavedPhotoDao.allSavedIds(sol, roverName).combine(
            getAllRemoteMarsRoverPhotos(roverName, sol)
        ) { local, remote ->
            remote?.let { roverPhotoRemoteModel ->
                RoverPhotoUiState.Success(
                    roverPhotoRemoteModel.photos.map { photo ->
                        RoverPhotoUiModel(
                            id = photo.id,
                            roverName = photo.rover.name,
                            imgSrc = photo.imgSrc,
                            sol = photo.sol.toString(),
                            earthDate = photo.earthDate,
                            cameraFullName = photo.camera.fullName,
                            isSaved = local.contains(photo.id)
                        )
                    }
                )
            } ?: run {
                RoverPhotoUiState.Error
            }
        }

    suspend fun savePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.insert(toDbModel(roverPhotoUiModel))
    }

    suspend fun removePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.delete(toDbModel(roverPhotoUiModel))
    }

    fun getAllSaved(): Flow<RoverPhotoUiState> =
        marsRoverSavedPhotoDao.getAllSaved().map { localModel ->
            RoverPhotoUiState.Success(toUiModel(localModel))

        }


//    fun getMarsRoverPhoto(
//        roverName: String,
//        sol: String
//    ): Flow<RoverPhotoUiState> = flow {
//        try {
//            val networkRequest = marsRoverPhotoService.getMarsRoverPhotos(
//                roverName, sol
//            )
//            networkRequest.photos.map {
//                it
//            }
//            emit(RoverPhotoUiState.Success(networkRequest.photos.map { photo ->
//                RoverPhotoUiModel(
//                    id = photo.id,
//                    roverName = photo.rover.name,
//                    imgSrc = photo.imgSrc,
//                    sol = photo.sol.toString(),
//                    earthDate = photo.earthDate,
//                    cameraFullName = photo.camera.fullName
//                )
//            }))
//
//        } catch (throwale: Throwable) {
//            throwale.message
//            emit(RoverPhotoUiState.Error)
//        }
//    }
}