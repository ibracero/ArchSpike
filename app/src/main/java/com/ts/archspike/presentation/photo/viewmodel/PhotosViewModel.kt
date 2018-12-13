package com.ts.archspike.presentation.photo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import arrow.core.Either
import com.ts.archspike.data.PhotoRepository
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.model.Photo
import kotlinx.coroutines.*

class PhotosViewModel(private val repository: PhotoRepository) : ViewModel(), CoroutineScope {

    override val coroutineContext = Job() + Dispatchers.IO

    val photoLiveData = MutableLiveData<Either<NetworkException, List<Photo>>>()

    init {
        getPhotos()
    }

    fun getPhotos() {
        launch {
            val photos = async { repository.getPhotos() }.await()

            withContext(Dispatchers.Main) {
                photoLiveData.postValue(photos)
            }
        }
    }

    fun filterRandomly() {
        val filteredData = photoLiveData.value?.map { it.filterIndexed { i, _ -> i % 2 == 0 } }
        photoLiveData.postValue(filteredData)
    }
}