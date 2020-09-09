package com.evantemplate.cats.adapters

import android.media.Image
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evantemplate.cats.R
import com.evantemplate.cats.models.Cat

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Cat>?){
    val adapter = recyclerView.adapter as CatListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

