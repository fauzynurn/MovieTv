package com.example.movietv.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movietv.R
import com.example.movietv.data.model.Cast
import com.example.movietv.databinding.VhCastBinding

class CastAdapter(val list : List<Cast>) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    class CastViewHolder(val binding: VhCastBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : Cast){
            with(binding){
                name.text = data.name
                Glide.with(binding.root.context)
                    .load(binding.root.context.getString(R.string.base_image_url) + data.profilePath)
                    .apply(RequestOptions.placeholderOf(R.color.grey)
                        .error(R.color.grey))
                    .into(binding.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhCastBinding.inflate(inflater, parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}