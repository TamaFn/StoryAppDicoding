package com.example.storyappdicoding.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.remote.response.UploadResponse
import com.example.storyappdicoding.data.repository.StoryRepository
import java.io.File

class UploadViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun uploadImage(image: File, description: String): LiveData<Result<UploadResponse>> {
        return storyRepository.uploadStory(image, description)
    }
}