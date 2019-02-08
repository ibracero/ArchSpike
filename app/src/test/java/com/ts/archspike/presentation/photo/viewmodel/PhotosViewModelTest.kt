package com.ts.archspike.presentation.photo.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import arrow.core.Either
import arrow.core.Eval
import arrow.core.getOrElse
import com.nhaarman.mockitokotlin2.*
import com.ts.archspike.coroutines.TestCoroutineDispatcherProvider
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.PhotoRepository
import com.ts.archspike.domain.model.Photo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PhotosViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val photo1 = Photo(id = 1, albumId = 10, title = "title1", url = "url1", thumbnailUrl = "url1Thumbnail")
    private val photo2 = Photo(id = 2, albumId = 20, title = "title12", url = "url2", thumbnailUrl = "url2Thumbnail")
    private val photoList = listOf(photo1, photo2)

    private val photoRepository: PhotoRepository = mock()
    private val dispatcher = TestCoroutineDispatcherProvider()

    private lateinit var viewModel: PhotosViewModel
    private val observer: Observer<Either<NetworkException, List<Photo>>> = mock()

    private val argumentCaptor = argumentCaptor<Either<NetworkException, List<Photo>>>()

    @Before
    fun `Set up ViewModel`() {
        viewModel = PhotosViewModel(photoRepository, dispatcher)
        viewModel.photoLiveData.observeForever(observer)
    }

    @After
    fun `Remove observers`() {
        viewModel.photoLiveData.removeObserver(observer)
    }

    @Test
    fun `GIVEN a successful response WHEN get photos is called THEN post photos`() {
        runBlocking {
            whenever(photoRepository.getPhotos()).thenReturn(Either.right(photoList))

            viewModel.getPhotos()
        }

        verify(observer).onChanged(argumentCaptor.capture())
        argumentCaptor.firstValue.run {
            assertTrue(isRight())
            assertEquals(photo1, getOrElse { emptyList() }[0])
            assertEquals(photo2, getOrElse { emptyList() }[1])
        }
    }

    @Test
    fun `GIVEN a wrong response WHEN get photos is called THEN post exception`() {
        runBlocking {
            whenever(photoRepository.getPhotos()).thenReturn(Either.left(NetworkException.ServerException))

            viewModel.getPhotos()
        }

        verify(observer).onChanged(argumentCaptor.capture())

        argumentCaptor.firstValue.run {
            val value = foldLeft(Eval.now(NetworkException.ServerException)) { photos, _ -> photos }.value()

            assertTrue(isLeft())
            assertEquals(NetworkException.ServerException, value)
        }
    }

    @Test
    fun `GIVEN a list of elements WHEN filter randomly THEN post filtered photos`() {

        //Prepopulate livedata
        viewModel.photoLiveData.postValue(Either.right(photoList))

        viewModel.filterRandomly()

        verify(observer, times(2)).onChanged(argumentCaptor.capture())
        argumentCaptor.lastValue.run {
            assertTrue(isRight())
            assert(getOrElse { emptyList() }.size <= photoList.size)
        }
    }
}