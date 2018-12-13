package com.ts.archspike.presentation.photo

import android.arch.lifecycle.Observer
import android.os.Bundle
import arrow.core.Either
import com.ts.archspike.BaseActivity
import com.ts.archspike.R
import com.ts.archspike.common.di.photoListActivityModule
import com.ts.archspike.data.network.NetworkException
import com.ts.archspike.domain.model.Photo
import com.ts.archspike.presentation.gone
import com.ts.archspike.presentation.photo.viewmodel.PhotosViewModel
import com.ts.archspike.presentation.toast
import com.ts.archspike.presentation.visible
import com.ts.archspike.presentation.withViewModel
import kotlinx.android.synthetic.main.activity_photos.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class PhotosActivity : BaseActivity(), KodeinAware {

    private val photosViewModel by instance<PhotosViewModel>()

    private val adapter = PhotoAdapter()

    override fun activityModule() = Kodein.Module("am") {
        import(photoListActivityModule())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        photo_recycler_view.adapter = adapter

        showLoading()
        val photosViewModel = withViewModel({ photosViewModel }) {
            photoLiveData.observe(this@PhotosActivity, Observer { updateState(it) })
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