package com.ts.archspike.data.network

import com.ts.archspike.domain.model.Photo
import retrofit2.Call
import retrofit2.http.GET

interface PhotoApi {

    @GET("/photos")
    fun getPhotos(): Call<List<Photo>>
}