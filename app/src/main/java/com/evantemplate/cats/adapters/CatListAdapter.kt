package com.evantemplate.cats.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.evantemplate.cats.databinding.ItemCatBinding
import com.evantemplate.cats.models.Cat

class CatListAdapter(val onLastPosition: (Boolean) -> Unit ): ListAdapter<Cat, CatListAdapter.CatViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)
        holder.bind(cat)

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