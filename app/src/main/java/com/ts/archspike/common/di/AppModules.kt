package com.ts.archspike.common.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ts.archspike.data.PhotoRepositoryImpl
import com.ts.archspike.data.network.PhotoApi
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://jsonplaceholder.typicode.com"
private const val CONNECT_TIMEOUT = 10L
private const val READ_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 10L

val appModule = Kodein.Module("app") {

    import(okHttpModule)
    import(retrofitModule)
    import(photosDataModule)
}

val okHttpModule = Kodein.Module("http") {
    bind<OkHttpClient>() with singleton {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.build()
    }
}

val retrofitModule = Kodein.Module("retrofitModule") {
    bind<Retrofit>() with singleton {
        val okHttpClient: OkHttpClient = instance()
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }
}

val photosDataModule = Kodein.Module("photosData") {
    bind<PhotoApi>() with provider {
        val retrofit: Retrofit = instance()
        retrofit.create(PhotoApi::class.java)
    }

    bind<PhotoRepositoryImpl>() with singleton {
        PhotoRepositoryImpl(instance() as PhotoApi)
    }
}