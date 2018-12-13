package com.ts.archspike.data

import com.ts.archspike.data.network.PhotoApi
import com.ts.archspike.data.network.toEither

class PhotoRepository constructor(private val photoApi: PhotoApi) {

    suspend fun getPhotos() = photoApi.getPhotos().await().toEither()
}