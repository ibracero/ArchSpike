package com.ts.archspike.presentation.photo

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ts.archspike.R
import com.ts.archspike.domain.model.Photo

class ProfessionAdapter : ListAdapter<Photo, ProfessionViewHolder>(ProfessionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ProfessionViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_photo, parent, false)
            )

    override fun onBindViewHolder(holder: ProfessionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProfessionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(photo: Photo) {
        itemView?.findViewById<TextView>(R.id.profession_name)?.text = "Cat ${photo.id}"
        itemView?.findViewById<ImageView>(R.id.profession_image)?.let {

            val requestOptions = RequestOptions
                    .skipMemoryCacheOf(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()

            Glide.with(itemView.context)
                    .load("https://cataas.com/cat")
                    .apply(requestOptions)
                    .into(it)
        }
    }
}

class ProfessionDiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo?, newItem: Photo?) =
            oldItem?.id == newItem?.id

    override fun areContentsTheSame(oldItem: Photo?, newItem: Photo?) =
            oldItem == newItem
}
