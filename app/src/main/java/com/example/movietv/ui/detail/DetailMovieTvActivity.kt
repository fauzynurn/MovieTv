package com.example.movietv.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movietv.R
import com.example.movietv.databinding.ActivityMovieTvDetailBinding
import com.example.movietv.ui.home.MovieTvViewModel
import com.example.movietv.utils.Constant
import com.example.movietv.utils.DateTimeConverter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieTvActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMovieTvDetailBinding
    private val viewModel by viewModel(MovieTvViewModel::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_tv_detail)
        var id = intent.getIntExtra(Constant.MOVIE_ID, -1)
        if (id == -1) {
            id = intent.getIntExtra(Constant.TVSHOW_ID, -1)
            viewModel.getTvShow(id)
        }else{
            viewModel.getMovie(id)
        }
        viewModel.movieTv.observe(this, Observer {
            data ->
            with(dataBinding) {
                Glide.with(this@DetailMovieTvActivity)
                    .load(getString(R.string.base_image_url) + data?.posterUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.color.grey)
                            .error(R.color.grey)
                    )
                    .into(dataBinding.posterImg)
                Glide.with(this@DetailMovieTvActivity)
                    .load(getString(R.string.base_image_url) + data?.backdropUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.color.grey)
                            .error(R.color.grey)
                    )
                    .into(dataBinding.backdropImg)
                title.text = data?.title
                if (!data?.releasedDate.isNullOrEmpty()) {
                    releaseDate.text = String.format("Released at %s", data?.releasedDate)
                } else {
                    releaseDate.visibility = View.GONE
                }
                genreList.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = GenreAdapter(data?.genre ?: listOf())
                }
                rating.text = data?.rating.toString()
                vote.text = String.format("%s rated", data?.vote.toString())
                duration.text = data?.duration?.let {
                    DateTimeConverter.convertMinutesToHourMinutes(
                        it
                    )
                }
                language.text = data?.language
                overview.text = data?.overview
                if (data?.castList.isNullOrEmpty()) {
                    castTitle.visibility = View.GONE
                } else {
                    castList.apply {
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = CastAdapter(data.castList ?: listOf())
                    }
                }
            }
        })
    }
}