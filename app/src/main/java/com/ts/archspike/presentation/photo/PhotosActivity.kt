package com.ts.archspike.presentation.photo

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import arrow.core.Either
import com.ts.archspike.R
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.model.Photo
import com.ts.archspike.presentation.gone
import com.ts.archspike.presentation.photo.viewmodel.PhotosViewModel
import com.ts.archspike.presentation.toast
import com.ts.archspike.presentation.visible
import kotlinx.android.synthetic.main.activity_photos.*
import org.koin.android.viewmodel.ext.android.viewModel

class PhotosActivity : AppCompatActivity() {

    private val photosViewModel: PhotosViewModel by viewModel()

    private val adapter = PhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        photo_recycler_view.adapter = adapter

        showLoading()
        photosViewModel.run {
            photoLiveData.observe(this@PhotosActivity, Observer { updateState(it) })
            getPhotos()
        }

        kill_random_button.setOnClickListener {
            photosViewModel.filterRandomly()
        }
        reload_button.setOnClickListener {
            showLoading()
            photosViewModel.getPhotos()
        }
    }

    private fun updateState(data: Either<NetworkException, List<Photo>>?) {
        loading_view.gone()
        data?.fold(
                { toast("Error retrieving data... Try again later") },
                { adapter.submitList(it) }
        )
    }

    private fun showLoading() {
        loading_view.visible()
    }
}