package com.example.storyappdicoding.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyappdicoding.data.remote.response.StoryItem
import com.example.storyappdicoding.databinding.ItemStoryBinding
import com.example.storyappdicoding.utils.withDateFormat

class StoryAdapter: ListAdapter<StoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: StoryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        holder.storyData(story)
    }

    inner class ViewHolder(private val binding: ItemStoryBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun storyData(data: StoryItem) {
            binding.apply {
                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(data)
                }
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .into(ivItemPhoto)
                tvItemName.text = data.name
                tvDate.text = data.createdAt?.withDateFormat()
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem

            }
        }
    }
}