package com.ts.archspike.data.network

import com.ts.archspike.domain.model.Photo
import io.reactivex.Single
import retrofit2.http.GET

interface PhotoApi {

    @GET("/photos")
    fun getPhotos(): Single<List<Photo>>
}