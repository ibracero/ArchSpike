package com.ts.archspike.presentation.photo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ts.archspike.data.PhotoRepository
import com.ts.archspike.domain.model.Photo
import com.ts.archspike.presentation.photo.Data
import com.ts.archspike.presentation.photo.DataState
import kotlinx.coroutines.*

class PhotosViewModel(private val repository: PhotoRepository) : ViewModel(), CoroutineScope {

    override val coroutineContext = Job() + Dispatchers.IO

    val photoLiveData = MutableLiveData<Data<List<Photo>>>()

    init {
        getPhotos()
    }

    fun getPhotos() {
        launch {
            val photos = async { repository.getPhotos() }.await()

            withContext(Dispatchers.Main) {
                photoLiveData.postValue(Data(dataState = DataState.SUCCESS, data = photos))
            }
        }
    }

    override fun onCleared() {
    }

    fun filterRandomly() {
        val currentData = photoLiveData.value?.data
        photoLiveData.postValue(
                Data(dataState = DataState.SUCCESS,
                        data = currentData?.filterIndexed { i, _ -> i % 2 == 0 })
        )
    }
}