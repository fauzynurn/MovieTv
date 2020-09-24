package com.example.movietv.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.movietv.R
import com.example.movietv.databinding.ActivityMainBinding
import com.example.movietv.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val mAdapter = HomeFragmentAdapter(this, supportFragmentManager)
        with(dataBinding){
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            viewPager.adapter = mAdapter
            tabLayout.setupWithViewPager(viewPager)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_favorite -> {
                startActivity(Intent(this,FavoriteActivity::class.java))
            }
        }
        return true
    }
}