package com.ts.archspike.presentation.photo

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.ts.archspike.R
import com.ts.archspike.domain.model.Photo

class PhotoAdapter : ListAdapter<Photo, PhotoViewHolder>(PhotoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PhotoViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_photo, parent, false)
            )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(photo: Photo) {
        itemView.findViewById<TextView>(R.id.profession_name)?.text = photo.title
        itemView.findViewById<ImageView>(R.id.profession_image)?.let {
            Picasso.get().load(photo.thumbnailUrl)
                    .into(it)
        }
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
            oldItem == newItem
}
