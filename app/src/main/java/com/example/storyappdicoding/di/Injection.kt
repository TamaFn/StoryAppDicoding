package com.example.storyappdicoding.di

import android.content.Context
import com.example.storyappdicoding.data.local.pref.UserPreference
import com.example.storyappdicoding.data.local.pref.dataStore
import com.example.storyappdicoding.data.remote.retrofit.ApiConfig
import com.example.storyappdicoding.data.repository.StoryRepository
import com.example.storyappdicoding.data.repository.UserRepository
import kotlinx.coroutines.runBlocking


object Injection {
    fun provideAuthRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(apiService, pref)
    }
}