package com.example.movietv.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movietv.R
import com.example.movietv.callback.MovieTvCallback
import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.model.MovieModel
import com.example.movietv.databinding.VhItemBinding
import com.example.movietv.utils.Constant.LOADING_ITEM
import com.example.movietv.utils.Constant.REGULAR_ITEM

class MovieAdapter(val callback : MovieTvCallback<MovieEntity>) : PagingDataAdapter<MovieEntity, MovieAdapter.MovieTvViewHolder>(DIFF_CALLBACK) {

    class MovieTvViewHolder(val context: Context, val binding: VhItemBinding, val callback : MovieTvCallback<MovieEntity>) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : MovieEntity){
            with(binding){
                root.setOnClickListener { callback.onClick(data.id) }
                Glide.with(binding.root.context)
                    .load(binding.root.context.getString(R.string.base_image_url) + data.posterUrl)
                    .apply(RequestOptions.placeholderOf(R.color.grey)
                        .error(R.color.grey))
                    .into(binding.image)
                vote.text = String.format("%d people vote",data.vote)
                rating.text = data.rating.toString()
                title.text = data.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhItemBinding.inflate(inflater, parent, false)
        return MovieTvViewHolder(parent.context,binding, callback)
    }

    override fun onBindViewHolder(holder: MovieTvViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int): Int = if(position == itemCount) REGULAR_ITEM else LOADING_ITEM

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieEntity> = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}