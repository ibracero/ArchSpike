package com.ts.archspike.presentation.photo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import arrow.core.Either
import com.ts.archspike.common.coroutines.CoroutineDispatcherProvider
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.PhotoRepository
import com.ts.archspike.domain.model.Photo
import kotlinx.coroutines.*
import kotlin.random.Random

class PhotosViewModel(private val repository: PhotoRepository,
                      private val dispatcher: CoroutineDispatcherProvider)
    : ViewModel() {

    private val scope = object : CoroutineScope {
        private val photosJob = Job()
        override val coroutineContext = photosJob + dispatcher.io
    }

    val photoLiveData = MutableLiveData<Either<NetworkException, List<Photo>>>()

    fun getPhotos() {
        scope.launch {
            val photos = repository.getPhotos()

            withContext(dispatcher.main) {
                photoLiveData.postValue(photos)
            }
        }
    }

    fun filterRandomly() {
        scope.launch {
            val filteredData = photoLiveData.value
                    ?.map { it.filter { photo -> photo.title.length % Random.nextInt(1, 10) == 0 } }

            withContext(dispatcher.main) {
                photoLiveData.postValue(filteredData)
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}