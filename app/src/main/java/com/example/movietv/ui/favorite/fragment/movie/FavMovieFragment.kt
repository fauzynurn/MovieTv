package com.example.movietv.ui.favorite.fragment.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietv.R
import com.example.movietv.callback.MovieTvCallback
import com.example.movietv.data.model.MovieModel
import com.example.movietv.databinding.FragmentMovieBinding
import com.example.movietv.ui.detail.DetailMovieTvActivity
import com.example.movietv.ui.home.MovieAdapter
import com.example.movietv.ui.home.MovieTvViewModel
import com.example.movietv.utils.Constant
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavMovieFragment : Fragment() {
    private val mDisposable = CompositeDisposable()
    private val viewModel by viewModel(MovieTvViewModel::class)
    private lateinit var dataBinding : FragmentMovieBinding
    private lateinit var mAdapter: MovieAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       dataBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = MovieAdapter(object : MovieTvCallback<MovieModel>{
            override fun onClick(id : Long) {
                startActivity(
                    Intent(activity,DetailMovieTvActivity::class.java)
                        .putExtra(Constant.MOVIE_ID,id)
                )
            }

            override fun onFavIconClicked(item : MovieModel, isFav : Boolean) {

            }
        })
        with(dataBinding){
            movieRv.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        mDisposable.add(viewModel.getFavMovieList().subscribe {
            mAdapter.submitData(lifecycle,it)
        })
    }

}