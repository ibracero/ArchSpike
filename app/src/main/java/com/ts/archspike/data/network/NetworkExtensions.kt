package com.ts.archspike.data.network

import arrow.core.Either
import retrofit2.Response

fun <T> Response<T>.toEither(): Either<NetworkException, T> {
    val rawResponseCode = raw().networkResponse()?.code() ?: code()
    val body = this.body()
    return when {
        rawResponseCode !in 200 until 300 -> Either.left(NetworkException.ServerException)
        body != null -> Either.right(body)
        else -> Either.left(NetworkException.UnknownException)
    }
}