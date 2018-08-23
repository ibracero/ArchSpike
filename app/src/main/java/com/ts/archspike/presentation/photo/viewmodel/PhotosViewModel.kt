package com.ts.archspike.presentation.photo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ts.archspike.data.PhotoRepository
import com.ts.archspike.domain.model.Photo
import com.ts.archspike.presentation.photo.Data
import com.ts.archspike.presentation.photo.DataState

class PhotosViewModel(private val repository: PhotoRepository) : ViewModel() {

    val professions = MutableLiveData<Data<List<Photo>>>()

    init {
        getProfessions()
    }

    fun getProfessions() {
        repository.getProfessions()
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