package com.ts.archspike.common.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ts.archspike.common.coroutines.CoroutineDispatcherProvider
import com.ts.archspike.data.PhotoRepositoryImpl
import com.ts.archspike.data.network.PhotoApi
import com.ts.archspike.domain.PhotoRepository
import com.ts.archspike.presentation.photo.viewmodel.PhotosViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://jsonplaceholder.typicode.com"
private const val CONNECT_TIMEOUT = 10L
private const val READ_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 10L

val retrofitModule = module {

    single<OkHttpClient> {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
                .client(get())
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    single<PhotoApi> {
        get<Retrofit>().create(PhotoApi::class.java)
    }
}

val repositoryModule = module {

    single<PhotoRepository> {
        PhotoRepositoryImpl(get())
    }
}

val viewModelModule = module {

    single { CoroutineDispatcherProvider() }

    viewModel { PhotosViewModel(get(), get()) }
}