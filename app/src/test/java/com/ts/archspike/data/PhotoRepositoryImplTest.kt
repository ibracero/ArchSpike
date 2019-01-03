package com.ts.archspike.data

import arrow.core.Either
import arrow.core.Eval
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.PhotoRepository
import com.ts.archspike.domain.model.Photo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class PhotoRepositoryImplTest {

    companion object {
        private val ANY_PHOTO = Photo(
                id = 1,
                albumId = 1,
                title = "title",
                url = "url.com",
                thumbnailUrl = "thumbnail")
    }

    private val repository: PhotoRepository = mock()

    @Test
    fun `GIVEN a successful response WHEN getPhotos is called THEN returns a list of photos`() {
        runBlocking {
            whenever(repository.getPhotos()).thenReturn(Either.right(listOf(ANY_PHOTO)))

            val result = repository.getPhotos()
            val value = result.foldRight(Eval.now(listOf<Photo>())) { photos, _ ->
                Eval.now(photos)
            }.value()

            assertTrue(result.isRight())
            assertEquals(listOf(ANY_PHOTO), value)
        }
    }

    @Test
    fun `GIVEN an error response WHEN getPhotos is called THEN returns a NetworkException`() {
        runBlocking {
            whenever(repository.getPhotos()).thenReturn(Either.left(NetworkException.ServerException))

            val result = repository.getPhotos()
            val value = result.foldLeft(Eval.now(NetworkException.ServerException)) { photos, _ ->
                photos
            }.value()

            assertTrue(result.isLeft())
            assert(value is NetworkException.ServerException)
        }
    }
}