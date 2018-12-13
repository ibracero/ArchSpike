package com.ts.archspike.data.network

sealed class NetworkException {
    object NoConnectivityException : NetworkException()
    object ServerException : NetworkException()
    object UnknownException : NetworkException()
}