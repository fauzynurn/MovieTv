package com.example.movietv.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movietv.R
import com.example.movietv.data.Resource
import com.example.movietv.data.model.MovieModel
import com.example.movietv.databinding.ActivityMovieDetailBinding
import com.example.movietv.ui.home.MovieTvViewModel
import com.example.movietv.utils.Constant
import com.example.movietv.utils.DateTimeUtil
import com.google.android.material.button.MaterialButton
import com.jakewharton.rxbinding.view.RxView
import org.koin.androidx.viewmodel.ext.android.viewModel
import rx.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MovieDetailActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMovieDetailBinding
    private val viewModel by viewModel(MovieTvViewModel::class)
    private var movie : MovieModel? = null
    var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        val id = intent.getLongExtra(Constant.ID, -1)
        dataBinding.movieContent.visibility = View.GONE
        dataBinding.movieDetailProgressBar.visibility = View.VISIBLE
        viewModel.getMovie(id).observe(this, {
            when (it) {
                is Resource.Success -> {
                    movie = it.data
                    with(dataBinding) {
                        movieContent.visibility = View.VISIBLE
                        movieDetailProgressBar.visibility = View.GONE
                        it.data?.isFav?.let {
                            isFavorite = it
                            favButton.toggleFavoriteBtn(isFavorite)
                        }
                        Glide.with(this@MovieDetailActivity)
                            .load(getString(R.string.base_image_url) + it.data?.posterUrl)
                            .apply(
                                RequestOptions.placeholderOf(R.color.grey)
                                    .error(R.color.grey)
                            )
                            .into(dataBinding.posterImg)
                        Glide.with(this@MovieDetailActivity)
                            .load(getString(R.string.base_image_url) + it.data?.backdropUrl)
                            .apply(
                                RequestOptions.placeholderOf(R.color.grey)
                                    .error(R.color.grey)
                            )
                            .into(dataBinding.backdropImg)
                        title.text = String.format(
                            "%s (%s)",
                            it.data?.title,
                            DateTimeUtil.getYear(
                                SimpleDateFormat("yyyy-MM-dd", Locale.US),
                                it.data?.releasedDate ?: ""
                            )
                        )
                        genreList.apply {
                            layoutManager = LinearLayoutManager(
                                context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            adapter = GenreAdapter(it.data?.genres ?: listOf())
                        }
                        rating.text = it.data?.rating.toString()
                        vote.text = String.format("%s rated", it.data?.vote.toString())
                        duration.text = it.data?.duration?.let {
                            DateTimeUtil.convertMinutesToHourMinutes(
                                it
                            )
                        }
                        language.text = it.data?.language
                        overview.text = it.data?.overview
                        if (it.data?.castList.isNullOrEmpty()) {
                            castTitle.visibility = View.GONE
                        } else {
                            castList.apply {
                                layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                adapter = CastAdapter(it.data?.castList?.take(5) ?: listOf())
                            }
                        }
                    }
                }
                is Resource.Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

            }
        })
        RxView.clicks(dataBinding.favButton)
            .map {
                dataBinding.favButton.toggleFavoriteBtn(!isFavorite)}
            .debounce(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                movie?.let {
                    viewModel.setFavoriteMovie(it,!isFavorite)
                }
            }
    }

    fun MaterialButton.toggleFavoriteBtn(isFavorite : Boolean){
        this.isSelected = isFavorite
        this.text = if(isFavorite) getString(R.string.added_to_favorite) else getString(
            R.string.add_to_favorite
        )}
}