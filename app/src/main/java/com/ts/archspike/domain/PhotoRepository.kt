package com.ts.archspike.domain

import arrow.core.Either
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.model.Photo

interface PhotoRepository{

    suspend fun getPhotos(): Either<NetworkException, List<Photo>>
}