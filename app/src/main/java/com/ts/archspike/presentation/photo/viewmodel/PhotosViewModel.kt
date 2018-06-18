package com.ts.archspike.presentation.photo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ts.archspike.data.PhotoRepository
import com.ts.archspike.domain.model.Photo
import com.ts.archspike.presentation.photo.Data
import com.ts.archspike.presentation.photo.DataState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PhotosViewModel(private val repository: PhotoRepository) : ViewModel() {

    val professions = MutableLiveData<Data<List<Photo>>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        getProfessions()
    }

    fun getProfessions() {
        compositeDisposable.add(repository.getProfessions()
                .doOnSubscribe {
                    professions.postValue(
                            Data(dataState = DataState.LOADING, data = professions.value?.data)
                    )
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    professions.postValue(
                            Data(dataState = DataState.SUCCESS, data = it)
                    )
                }, {
                    professions.postValue(
                            Data(dataState = DataState.ERROR, data = professions.value?.data)
                    )
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun filterRandomly() {
        val currentData = professions.value?.data
        professions.postValue(
                Data(dataState = DataState.SUCCESS,
                        data = currentData?.filterIndexed { i, _ -> i % 2 == 0 })
        )
    }
}