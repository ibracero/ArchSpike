package com.ts.archspike.common.di

import com.ts.archspike.common.CoroutineDispatcherProvider
import com.ts.archspike.presentation.photo.viewmodel.PhotosViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

fun photoListActivityModule() = Kodein.Module("photoList") {
    bind<CoroutineDispatcherProvider>() with provider { CoroutineDispatcherProvider() }
    bind<PhotosViewModel>() with provider { PhotosViewModel(instance(), instance()) }
}