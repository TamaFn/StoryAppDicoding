package com.example.storyappdicoding.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.storyappdicoding.data.local.pref.UserModel
import com.example.storyappdicoding.data.remote.response.StoryItem
import com.example.storyappdicoding.data.repository.UserRepository
import com.example.storyappdicoding.data.repository.StoryRepository
import kotlinx.coroutines.launch
import androidx.paging.cachedIn

class MainViewModel(
    private val storyRepository: StoryRepository,
    private val repository: UserRepository
) : ViewModel() {

    private val refresh = MutableLiveData<Unit>()

    init {
        refreshData()
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.userLogout()
        }
    }

    val story: LiveData<PagingData<StoryItem>> = refresh.switchMap {
        storyRepository.getAllStory().cachedIn(viewModelScope)
    }

    fun refreshData() {
        refresh.value = Unit
    }
}