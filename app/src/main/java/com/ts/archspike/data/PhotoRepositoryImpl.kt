package com.ts.archspike.data

import com.ts.archspike.data.network.PhotoApi
import com.ts.archspike.data.network.toEither
import com.ts.archspike.domain.PhotoRepository

class PhotoRepositoryImpl constructor(private val photoApi: PhotoApi) : PhotoRepository {

    override suspend fun getPhotos() = photoApi.getPhotos().await().toEither()
}