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
import com.example.movietv.data.entity.TvShowEntity
import com.example.movietv.databinding.VhItemBinding
import com.example.movietv.utils.Constant

class TvShowAdapter(val callback : MovieTvCallback<TvShowEntity>) : PagingDataAdapter<TvShowEntity, TvShowAdapter.MovieTvViewHolder>(DIFF_CALLBACK) {

    class MovieTvViewHolder(val context: Context, val binding: VhItemBinding, val callback : MovieTvCallback<TvShowEntity>) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : TvShowEntity){
            with(binding){
                root.setOnClickListener { callback.onClick(data.id) }
                Glide.with(binding.root.context)
                    .load(binding.root.context.getString(R.string.base_image_url) + data.posterUrl)
                    .apply(RequestOptions.placeholderOf(R.color.grey)
                        .error(R.color.grey))
                    .into(binding.image)
                vote.text = kotlin.String.format("%d people vote",data.vote)
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

    override fun getItemViewType(position: Int): Int = if(position == itemCount) Constant.REGULAR_ITEM else Constant.LOADING_ITEM

    override fun onBindViewHolder(holder: MovieTvViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TvShowEntity> = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldNote: TvShowEntity, newNote: TvShowEntity): Boolean {
                return oldNote.id == newNote.id
            }

            override fun areContentsTheSame(oldNote: TvShowEntity, newNote: TvShowEntity): Boolean {
                return oldNote == newNote
            }
        }
    }
}