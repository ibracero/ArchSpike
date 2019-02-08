package com.ts.archspike.presentation.photo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import arrow.core.Either
import com.ts.archspike.common.CoroutineDispatcherProvider
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.PhotoRepository
import com.ts.archspike.domain.model.Photo
import kotlinx.coroutines.*
import kotlin.random.Random

class PhotosViewModel(private val repository: PhotoRepository,
                      private val dispatcher: CoroutineDispatcherProvider)
    : ViewModel(), CoroutineScope {

    private val photosJob = Job()

    override val coroutineContext = photosJob + dispatcher.io

    val photoLiveData = MutableLiveData<Either<NetworkException, List<Photo>>>()

    fun getPhotos() {
        launch {
            val photos = withContext(dispatcher.default) {
                repository.getPhotos()
            }

            withContext(dispatcher.ui) {
                photoLiveData.postValue(photos)
            }
        }
    }

    fun filterRandomly() {
        launch {
            val filteredData = photoLiveData.value
                    ?.map { it.filter { photo -> photo.title.length % Random.nextInt(1, 10) == 0 } }

            withContext(dispatcher.ui) {
                photoLiveData.postValue(filteredData)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        photosJob.cancel()
    }
}