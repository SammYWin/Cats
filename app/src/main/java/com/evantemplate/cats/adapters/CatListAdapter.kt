package com.evantemplate.cats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evantemplate.cats.R
import com.evantemplate.cats.models.Cat
import kotlinx.android.synthetic.main.item_cat.view.*

class CatListAdapter(val isOnLastPosition: (Boolean) -> Unit, val onClick: (Cat) -> Unit) :
    RecyclerView.Adapter<CatListAdapter.CatViewHolder>() {

    var items: List<Cat> = listOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CatViewHolder(inflater.inflate(R.layout.item_cat, parent, false))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = items[position]
        holder.bind(cat, onClick)

        val img = ResourcesCompat.getDrawable(
            holder.itemView.resources, R.drawable.ic_baseline_favorite_border_24,
            holder.itemView.context.theme
        )
        val imgRed = ResourcesCompat.getDrawable(
            holder.itemView.resources, R.drawable.ic_baseline_favorite_red_24,
            holder.itemView.context.theme
        )

        if (!cat.isInFavorites) {
            holder.itemView.btn_fav.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null)
            holder.itemView.btn_fav.text =
                holder.itemView.context.getString(R.string.btn_text_favorite)
        } else {
            holder.itemView.btn_fav.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                imgRed,
                null
            )
            holder.itemView.btn_fav.text =
                holder.itemView.context.getString(R.string.btn_text_delete)
        }

        holder.itemView.btn_fav.setOnClickListener {
            onClick(cat)

            if (!cat.isInFavorites) {
                it.btn_fav.setCompoundDrawablesWithIntrinsicBounds(null, null, imgRed, null)
                it.btn_fav.text = it.context.getString(R.string.btn_text_delete)
                cat.isInFavorites = !cat.isInFavorites
            } else {
                it.btn_fav.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null)
                if ((it.parent.parent as View).id == R.id.rv_cats_all)
                    it.btn_fav.text = it.context.getString(R.string.btn_text_favorite)
                cat.isInFavorites = !cat.isInFavorites
            }
        }

        isOnLastPosition(position == itemCount - 1)
    }

    fun updateData(data: List<Cat>) {
        val diffCallBack = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].id == data[newPos].id
            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos] == data[newPos]
            override fun getOldListSize(): Int = items.size
            override fun getNewListSize(): Int = data.size
        }
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }

    class CatViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {
        fun bind(cat: Cat, onClick: (Cat) -> Unit) {
            Glide.with(itemView.iv_cat.context)
                .load(cat.imgUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(itemView.iv_cat)

            itemView.btn_fav.setOnClickListener {
                onClick(cat)
            }
        }
    }
}