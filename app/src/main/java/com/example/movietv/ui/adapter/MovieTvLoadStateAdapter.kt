package com.example.movietv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietv.callback.LoadStateCallback
import com.example.movietv.databinding.LoadStateBinding
import com.example.movietv.databinding.VhItemBinding
import com.example.movietv.ui.home.MovieAdapter

class MovieTvLoadStateAdapter(
    val callback : LoadStateCallback
) : LoadStateAdapter<MovieTvLoadStateAdapter.NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LoadStateBinding.inflate(inflater, parent, false)
        return NetworkStateItemViewHolder(binding) { callback.onRetry() }
    }

    class NetworkStateItemViewHolder(
        val binding : LoadStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(loadState: LoadState) {
            with(binding){
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                retryButton.setOnClickListener { retryCallback() }
                errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}