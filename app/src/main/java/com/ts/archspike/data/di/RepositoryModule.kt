package com.ts.archspike.data.di

import com.github.salomonbrys.kodein.*
import com.ts.archspike.data.PhotoRepository
import com.ts.archspike.data.network.PhotoApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = Kodein.Module {

    val BASE_URL = "https://jsonplaceholder.typicode.com"
    val CONNECT_TIMEOUT = 10L
    val READ_TIMEOUT = 10L
    val WRITE_TIMEOUT = 10L

    bind<OkHttpClient>() with singleton {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.build()
    }

    bind<Retrofit>() with singleton {
        val okHttpClient: OkHttpClient = kodein.instance()

        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    bind<PhotoApi>() with provider {
        val retrofit: Retrofit = kodein.instance()
        retrofit.create(PhotoApi::class.java)
    }

    bind<PhotoRepository>() with singleton {
        val photoApi: PhotoApi = kodein.instance()
        PhotoRepository(photoApi)
    }
}