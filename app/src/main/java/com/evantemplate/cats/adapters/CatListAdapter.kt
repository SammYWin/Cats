package com.evantemplate.cats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.evantemplate.cats.R
import com.evantemplate.cats.databinding.ItemCatBinding
import com.evantemplate.cats.models.Cat
import kotlinx.android.synthetic.main.item_cat.view.*

class CatListAdapter(val onLastPosition: (Boolean) -> Unit, val onClick: (Cat) -> Unit): ListAdapter<Cat, CatListAdapter.CatViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)
        holder.bind(cat)

        val img = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_baseline_favorite_border_24,
            holder.itemView.context.theme)
        val imgRed = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_baseline_favorite_red_24,
            holder.itemView.context.theme)

        if(!cat.isInFavorites) {
            holder.itemView.btn_fav.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null)
            holder.itemView.btn_fav.text = holder.itemView.context.getString(R.string.btn_text_favorite)
        }
        else{
                holder.itemView.btn_fav.setCompoundDrawablesWithIntrinsicBounds(null, null, imgRed, null)
                holder.itemView.btn_fav.text = holder.itemView.context.getString(R.string.btn_text_delete)
            }

        holder.itemView.btn_fav.setOnClickListener {
            onClick(cat)

            if(!cat.isInFavorites) {
                it.btn_fav.setCompoundDrawablesWithIntrinsicBounds(null, null, imgRed, null)
                it.btn_fav.text = it.context.getString(R.string.btn_text_delete)
                cat.isInFavorites = !cat.isInFavorites
            }
            else {
                it.btn_fav.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null)
                if((it.parent.parent as View).id == R.id.rv_cats_all)
                    it.btn_fav.text = it.context.getString(R.string.btn_text_favorite)
                cat.isInFavorites = !cat.isInFavorites
            }
        }

        if(position == itemCount - 1)
            onLastPosition(true)
        else onLastPosition(false)
    }

    class CatViewHolder(private val binding: ItemCatBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cat: Cat){
            binding.cat = cat
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Cat>(){
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }

    }
}