package com.example.storyappdicoding.view.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyappdicoding.R
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.databinding.ActivityDetailBinding
import com.example.storyappdicoding.utils.getTimeAgo
import com.example.storyappdicoding.utils.getTimeMillisFromString
import com.example.storyappdicoding.utils.withDateFormat
import com.example.storyappdicoding.view.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyId = intent.getStringExtra(EXTRA_ID)
        storyId?.run(viewModel::setStoryId)

        actionBar()
        setupViewModel()
    }

    private fun actionBar() {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel(){
        viewModel.detailStory.observe(this) { result ->
            when (result) {
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> { showLoading(true) }
                is Result.Success -> {
                    showLoading(false)
                    val story = result.data
                    binding.apply {
                        Glide.with(this@DetailActivity)
                            .load(story.photoUrl)
                            .into(binding.detailUserImage)
                        binding.detailUserName.text = story.name
                        binding.detailTime.text = story.createdAt?.withDateFormat()
                        binding.detailUserDescription.text = story.description
                        binding.detailHistoryTime.text = getTimeAgo(binding.root.context, getTimeMillisFromString(result.data.createdAt.toString()))
                    }
                    supportActionBar?.title = getString(R.string.detail_title, story.name)
                }

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "id"
    }


}