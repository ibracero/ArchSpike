package com.ts.archspike.data

import arrow.core.Either
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.data.network.PhotoApi
import com.ts.archspike.data.network.toEither
import com.ts.archspike.domain.model.Photo

class PhotoRepository constructor(private val photoApi: PhotoApi) {

    suspend fun getPhotos(): Either<NetworkException, List<Photo>> {
        return photoApi.getPhotos().await()
                .toEither()
    }
}