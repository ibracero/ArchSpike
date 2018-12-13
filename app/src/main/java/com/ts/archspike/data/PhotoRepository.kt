package com.ts.archspike.data

import com.ts.archspike.data.network.PhotoApi
import com.ts.archspike.domain.model.Photo

class PhotoRepository constructor(private val photoApi: PhotoApi) {

    suspend fun getPhotos(): List<Photo> = photoApi.getPhotos().await()
}