package com.ts.archspike.presentation.photo

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.ts.archspike.R
import com.ts.archspike.data.PhotoRepository
import com.ts.archspike.presentation.photo.viewmodel.PhotosViewModel
import com.ts.archspike.presentation.withViewModel
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector = KodeinInjector()

    private val repository: PhotoRepository by instance()

    private val adapter = ProfessionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()

        setContentView(R.layout.activity_photos)

        profession_recycler_view.layoutManager = GridLayoutManager(this, 2)
        profession_recycler_view.adapter = adapter

        val photosViewModel = withViewModel({ PhotosViewModel(repository) }) {
            professions.observe(this@PhotosActivity, Observer {
                adapter.submitList(it?.data)
                updateState(it?.dataState)
            })
        }

        random_button.setOnClickListener { photosViewModel.filterRandomly() }
        reload_button.setOnClickListener { photosViewModel.getProfessions() }
    }

    private fun updateState(dataState: DataState?) {
        when (dataState) {
            DataState.LOADING -> loading_view.visibility = View.VISIBLE
            DataState.SUCCESS -> loading_view.visibility = View.GONE
            DataState.ERROR -> {
                loading_view.visibility = View.GONE
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}