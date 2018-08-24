package com.ts.archspike.presentation.photo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ts.archspike.data.PhotoRepository
import com.ts.archspike.domain.model.Photo
import com.ts.archspike.presentation.photo.Data
import com.ts.archspike.presentation.photo.DataState
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class PhotosViewModel(private val repository: PhotoRepository) : ViewModel() {

    val professions = MutableLiveData<Data<List<Photo>>>()

    init {
        getProfessions()
    }

    fun getProfessions() {
        val job = async(CommonPool) {
            repository.getProfessions()
        }
        launch(UI) {
            professions.postValue(Data(dataState = DataState.SUCCESS, data = job.await()))
        }
    }

    override fun onCleared() {
    }

    fun filterRandomly() {
        val currentData = professions.value?.data
        professions.postValue(
                Data(dataState = DataState.SUCCESS,
                        data = currentData?.filterIndexed { i, _ -> i % 2 == 0 })
        )
    }
}