package com.example.movietv.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movietv.R
import com.example.movietv.callback.MovieTvCallback
import com.example.movietv.data.model.MovieModel
import com.example.movietv.databinding.VhItemBinding
import com.example.movietv.utils.DateTimeConverter

class MovieAdapter(val callback : MovieTvCallback<MovieModel>) : PagingDataAdapter<MovieModel, MovieAdapter.MovieTvViewHolder>(DIFF_CALLBACK) {

    class MovieTvViewHolder(val context: Context, val binding: VhItemBinding, val callback : MovieTvCallback<MovieModel>) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : MovieModel){
            with(binding){
                root.setOnClickListener { callback.onClick(data.id) }
                title.text = data.title
                Glide.with(binding.root.context)
                    .load(binding.root.context.getString(R.string.base_image_url) + data.posterUrl)
                    .apply(RequestOptions.placeholderOf(R.color.grey)
                        .error(R.color.grey))
                    .into(binding.image)
                genre.text = data.genre?.joinToString(",")
                rating.text = data.rating.toString()
                duration.text = DateTimeConverter.convertMinutesToHourMinutes(data.duration)
                favIcon.setOnClickListener {
                    callback.onFavIconClicked(data,data.isFav)
                }
                if(data.isFav) favIcon.setColorFilter(ContextCompat.getColor(context,R.color.pink)) else favIcon.setColorFilter(ContextCompat.getColor(context,R.color.white))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhItemBinding.inflate(inflater, parent, false)
        return MovieTvViewHolder(parent.context,binding, callback)
    }

    override fun onBindViewHolder(holder: MovieTvViewHolder, position: Int) {
        holder.bind(getItem(position) as MovieModel)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieModel> = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}