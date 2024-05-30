package com.example.storyappdicoding.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.local.pref.UserPreference
import com.example.storyappdicoding.data.remote.response.StoryItem
import com.example.storyappdicoding.data.remote.response.UploadResponse
import com.example.storyappdicoding.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun getAllStory(

    ): LiveData<Result<List<StoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response = apiService.listStory("Bearer $token")
            val result = response.listStory
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailStory(
        id: String
    ): LiveData<Result<StoryItem>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response = apiService.detailStory("Bearer $token", id)
            val result = response.story
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadStory(
        image: File,
        description: String
    ): LiveData<Result<UploadResponse>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val requestDescription = description.toRequestBody("text/plain".toMediaType())
            val requestImage = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "photo",
                image.name,
                requestImage
            )
            val response = apiService.uploadStory("Bearer $token", imageMultipart, requestDescription)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getLocationStory(): LiveData<Result<List<StoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response = apiService.listStory("Bearer $token", location = 1)
            val result = response.listStory
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}