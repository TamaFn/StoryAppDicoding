package com.example.storyappdicoding.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappdicoding.R
import com.example.storyappdicoding.databinding.ActivityMainBinding
import com.example.storyappdicoding.view.ViewModelFactory
import com.example.storyappdicoding.view.adapter.StoryAdapter
import com.example.storyappdicoding.view.detail.DetailActivity
import com.example.storyappdicoding.view.upload.UploadActivity
import com.example.storyappdicoding.view.welcome.WelcomeActivity
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.remote.response.StoryItem

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        checkSession()
        setAdapter()
        setRefresh()
        setUpViewModel()
        onClickCallback()
        uploadImage()
    }

    private fun setAdapter(){
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)
        storyAdapter = StoryAdapter()
        binding.rvStory.adapter = storyAdapter
    }

    private fun uploadImage(){
        binding.mainAddstory.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
    }


    private fun checkSession(){
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun onClickCallback() {
        storyAdapter.setOnItemCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryItem) {
                val intent = Intent(    this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }
        })
    }

    private fun setRefresh() {
        binding.refreshSwap.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun setUpViewModel(){
        viewModel.getAllStory().observe(this) { result ->
            when (result) {
                is Result.Error-> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    binding.refreshSwap.isRefreshing = false
                }
                is Result.Loading -> { showLoading(true) }
                is Result.Success -> {
                    showLoading(false)
                    storyAdapter.submitList(result.data)
                    binding.refreshSwap.isRefreshing = false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logout -> {
                viewModel.logout()
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.mainProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}