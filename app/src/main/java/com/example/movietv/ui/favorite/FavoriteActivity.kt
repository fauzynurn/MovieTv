package com.example.movietv.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.example.movietv.R
import com.example.movietv.databinding.ActivityMainBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var dataBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val mAdapter = FavFragmentAdapter(this, supportFragmentManager)
        with(dataBinding){
            setSupportActionBar(toolbar)
            title.text = getString(R.string.favorites)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            viewPager.adapter = mAdapter
            tabLayout.setupWithViewPager(viewPager)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return true
    }
}