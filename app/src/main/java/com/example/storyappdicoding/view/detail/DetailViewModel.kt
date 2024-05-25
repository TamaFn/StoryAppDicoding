package com.example.storyappdicoding.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.storyappdicoding.data.repository.StoryRepository

class DetailViewModel(private val storyRepository: StoryRepository): ViewModel() {

    private val _storyId = MutableLiveData<String>()
    val storyId: LiveData<String> = _storyId

    val detailStory = _storyId.switchMap {
        storyRepository.getDetailStory(it)
    }

    fun setStoryId(id: String) {
        _storyId.value = id
    }

}