package com.ts.archspike.data

import com.ts.archspike.data.network.PhotoApi
import com.ts.archspike.domain.model.Photo

class PhotoRepository constructor(private val photoApi: PhotoApi) {

    fun getProfessions(): List<Photo> = photoApi.getPhotos().execute().body() ?: emptyList()
}