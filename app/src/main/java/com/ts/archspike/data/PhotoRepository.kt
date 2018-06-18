package com.ts.archspike.data

import com.ts.archspike.data.network.PhotoApi
import com.ts.archspike.domain.model.Photo
import io.reactivex.Single

class PhotoRepository constructor(private val photoApi: PhotoApi) {

    fun getProfessions(): Single<List<Photo>> = photoApi.getPhotos()
}