package com.example.movietv.ui.home.fragment.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietv.R
import com.example.movietv.callback.MovieTvCallback
import com.example.movietv.databinding.FragmentMovieBinding
import com.example.movietv.databinding.FragmentTvshowBinding
import com.example.movietv.ui.detail.DetailMovieTvActivity
import com.example.movietv.ui.home.MovieTvAdapter
import com.example.movietv.ui.home.MovieTvViewModel
import com.example.movietv.utils.Constant
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {
    private val mDisposable = CompositeDisposable()
    private val viewModel by viewModel(MovieTvViewModel::class)
    private lateinit var dataBinding : FragmentTvshowBinding
    private lateinit var mAdapter: MovieTvAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_tvshow, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = MovieTvAdapter(object : MovieTvCallback{
            override fun onClick(id : Int) {
                startActivity(
                    Intent(activity,DetailMovieTvActivity::class.java)
                        .putExtra(Constant.TVSHOW_ID,id)
                )
            }

            override fun onFavIconClicked(id: Int, isFav : Boolean) {
                viewModel.setMovieAsFav(id)
                mAdapter.notifyDataSetChanged()
                Toast.makeText(context, "Item telah ditambahkan ke daftar favorit", Toast.LENGTH_SHORT).show()
            }
        })
        with(dataBinding){
            tvshowRv.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        mDisposable.add(viewModel.getTvShowList().subscribe {
            mAdapter.submitData(lifecycle,it)
        })
    }

}