package com.example.movietv.ui.home.fragment.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movietv.R
import com.example.movietv.callback.LoadStateCallback
import com.example.movietv.callback.MovieTvCallback
import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.model.MovieModel
import com.example.movietv.databinding.FragmentMovieBinding
import com.example.movietv.ui.adapter.MovieTvLoadStateAdapter
import com.example.movietv.ui.detail.MovieDetailActivity
import com.example.movietv.ui.home.MovieAdapter
import com.example.movietv.ui.home.MovieTvViewModel
import com.example.movietv.utils.Constant
import com.example.movietv.utils.Constant.LOADING_ITEM
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {
    private val mDisposable = CompositeDisposable()
    private val viewModel by viewModel(MovieTvViewModel::class)
    private lateinit var dataBinding: FragmentMovieBinding
    private lateinit var mAdapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = MovieAdapter(object : MovieTvCallback<MovieEntity> {
            override fun onClick(id: Long) {
                startActivity(
                    Intent(activity, MovieDetailActivity::class.java)
                        .putExtra(Constant.ID, id)
                )
            }
        })
        with(dataBinding) {
            movieRv.apply {
                adapter = mAdapter.withLoadStateFooter(
                    footer = MovieTvLoadStateAdapter(object : LoadStateCallback {
                        override fun onRetry() {
                            mAdapter.retry()
                        }
                    })
                )
                layoutManager = GridLayoutManager(context, 2)
                    .apply {
                    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
                        override fun getSpanSize(position: Int): Int = if(mAdapter.getItemViewType(position) == LOADING_ITEM) 1 else 2
                    }
                }
            }
        }
        mAdapter.addLoadStateListener {
            with(dataBinding){
                // Only show the list if refresh succeeds.
                movieRv.isVisible = it.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                progressBar.isVisible = it.source.refresh is LoadState.Loading

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = it.source.append as? LoadState.Error
                    ?: it.source.prepend as? LoadState.Error
                    ?: it.append as? LoadState.Error
                    ?: it.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        it.error.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
        mDisposable.add(viewModel.getMovieList().subscribe {
            mAdapter.submitData(lifecycle, it)
        })
    }

}