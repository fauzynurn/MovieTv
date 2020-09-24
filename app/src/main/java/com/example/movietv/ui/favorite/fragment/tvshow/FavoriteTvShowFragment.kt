package com.example.movietv.ui.favorite.fragment.tvshow

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietv.R
import com.example.movietv.callback.LoadStateCallback
import com.example.movietv.callback.MovieTvCallback
import com.example.movietv.data.entity.TvShowEntity
import com.example.movietv.data.model.TvShowModel
import com.example.movietv.databinding.FragmentTvshowBinding
import com.example.movietv.ui.adapter.MovieTvLoadStateAdapter
import com.example.movietv.ui.detail.MovieDetailActivity
import com.example.movietv.ui.detail.TvShowDetailActivity
import com.example.movietv.ui.home.MovieTvViewModel
import com.example.movietv.ui.home.TvShowAdapter
import com.example.movietv.utils.Constant
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvShowFragment : Fragment() {
    private val mDisposable = CompositeDisposable()
    private val viewModel by viewModel(MovieTvViewModel::class)
    private lateinit var dataBinding : FragmentTvshowBinding
    private lateinit var mAdapter: TvShowAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_tvshow, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = TvShowAdapter(object : MovieTvCallback<TvShowEntity>{
            override fun onClick(id : Long) {
                startActivity(
                    Intent(activity, TvShowDetailActivity::class.java)
                        .putExtra(Constant.ID,id)
                )
            }
        })
        with(dataBinding){
            tvshowRv.apply {
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
                            override fun getSpanSize(position: Int): Int = if(mAdapter.getItemViewType(position) == Constant.LOADING_ITEM) 1 else 2
                        }
                    }
            }
        }
        mAdapter.addLoadStateListener {
            with(dataBinding){
                // Only show the list if refresh succeeds.
                tvshowRv.isVisible = it.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                progressBar.isVisible = it.source.refresh is LoadState.Loading

                tvshowNoData.isVisible = mAdapter.itemCount == 0
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
        mDisposable.add(viewModel.getFavoriteTvShowList().subscribe {

            mAdapter.submitData(lifecycle, it)
        })
    }

}