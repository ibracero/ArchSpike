package com.ts.archspike.presentation.photo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import arrow.core.Either
import com.ts.archspike.data.PhotoRepositoryImpl
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.model.Photo
import kotlinx.coroutines.*
import kotlin.random.Random

class PhotosViewModel(private val repository: PhotoRepositoryImpl) : ViewModel(), CoroutineScope {

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
        launch {
            val filteredData = photoLiveData.value
                    ?.map { it.filter { photo -> photo.title.length % Random.nextInt(1, 10) == 0 } }

            withContext(Dispatchers.Main) {
                photoLiveData.postValue(filteredData)
            }
        }
    }
}