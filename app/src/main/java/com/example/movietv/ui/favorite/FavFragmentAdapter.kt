package com.example.movietv.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movietv.R
import com.example.movietv.ui.favorite.fragment.movie.FavMovieFragment
import com.example.movietv.ui.favorite.fragment.tvshow.FavTvShowFragment
import com.example.movietv.ui.home.fragment.movie.MovieFragment
import com.example.movietv.ui.home.fragment.tvshow.TvShowFragment

class FavFragmentAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TITLES = intArrayOf(R.string.movies, R.string.tv_shows)
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> FavMovieFragment()
            1 -> FavTvShowFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? = context.resources.getString(TITLES[position])

    override fun getCount(): Int = TITLES.size

}