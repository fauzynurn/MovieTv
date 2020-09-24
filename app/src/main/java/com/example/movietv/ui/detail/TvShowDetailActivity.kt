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
import com.example.movietv.data.model.TvShowModel
import com.example.movietv.databinding.ActivityTvShowDetailBinding
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


class TvShowDetailActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityTvShowDetailBinding
    private val viewModel by viewModel(MovieTvViewModel::class)
    private var tvShow : TvShowModel? = null
    var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_detail)
        dataBinding.tvContent.visibility = View.GONE
        dataBinding.tvDetailProgressBar.visibility = View.VISIBLE
        val id = intent.getLongExtra(Constant.ID, -1)
            viewModel.getTvShow(id).observe(this, {
                when (it) {
                    is Resource.Success -> {
                        tvShow = it.data
                        with(dataBinding) {
                            tvContent.visibility = View.VISIBLE
                            tvDetailProgressBar.visibility = View.GONE
                            it.data?.isFav?.let {
                                isFavorite = it
                                favButton.toggleFavoriteBtn(isFavorite)
                            }
                            Glide.with(this@TvShowDetailActivity)
                                .load(getString(R.string.base_image_url) + it.data?.posterUrl)
                                .apply(
                                    RequestOptions.placeholderOf(R.color.grey)
                                        .error(R.color.grey)
                                )
                                .into(dataBinding.posterImg)
                            Glide.with(this@TvShowDetailActivity)
                                .load(getString(R.string.base_image_url) + it.data?.backdropUrl)
                                .apply(
                                    RequestOptions.placeholderOf(R.color.grey)
                                        .error(R.color.grey)
                                )
                                .into(dataBinding.backdropImg)
                            title.text = it.data?.title
                            genreList.apply {
                                layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                adapter = GenreAdapter(it.data?.genre ?: listOf())
                            }
                            rating.text = it.data?.rating.toString()
                            vote.text = String.format("%s rated", it.data?.vote.toString())
                            totalEpisode.text = it.data?.totalEpisode.toString()
                            language.text = it.data?.language
                            overview.text = it.data?.overview
                            if (it.data?.nextEpisodeToAir != null) {
                                nextEpisodeLabel.visibility = View.VISIBLE
                                nextEpisodeContainer.visibility = View.VISIBLE
                                with(it.data.nextEpisodeToAir) {
                                    nextEpisodeName.text = name
                                    Glide.with(this@TvShowDetailActivity)
                                        .load(getString(R.string.base_image_url) + this.stillPath)
                                        .apply(
                                            RequestOptions.placeholderOf(R.color.grey)
                                                .error(R.color.grey)
                                        )
                                        .into(dataBinding.nextEpisodeImage)
                                    nextEpisodeTimeRemaining.text = String.format("Start in %d days", DateTimeUtil.getCurrentDiffWith(
                                        SimpleDateFormat("yyyy-MM-dd", Locale.US), this.airDate))
                                    episode.text = String.format("Episode %s",this.episodeNumber.toString())
                                }
                            }
                            if (it.data?.lastEpisodeToAir != null) {
                                lastEpisodeLabel.visibility = View.VISIBLE
                                lastEpisodeContainer.visibility = View.VISIBLE
                                with(it.data.lastEpisodeToAir) {
                                    lastEpisodeName.text = name
                                    Glide.with(this@TvShowDetailActivity)
                                        .load(getString(R.string.base_image_url) + this.stillPath)
                                        .apply(
                                            RequestOptions.placeholderOf(R.color.grey)
                                                .error(R.color.grey)
                                        )
                                        .into(dataBinding.lastEpisodeImage)
                                    lastEpisodeTime.text = String.format("Aired at %s",this.airDate)
                                    lastEpisodeRating.text = this.voteAverage.toString()
                                    lastEpisodeVotes.text = this.voteCount.toString()
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
                tvShow?.let {
                    viewModel.setFavoriteTvShow(it,!isFavorite)
                }
            }
    }

    fun MaterialButton.toggleFavoriteBtn(isFavorite : Boolean){
        this.isSelected = isFavorite
        this.text = if(isFavorite) getString(R.string.added_to_favorite) else getString(
            R.string.add_to_favorite
        )}
}