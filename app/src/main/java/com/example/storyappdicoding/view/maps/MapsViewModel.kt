package com.example.storyappdicoding.view.maps

import androidx.lifecycle.ViewModel
import com.example.storyappdicoding.data.repository.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getLocationStory() = storyRepository.getLocationStory()
}