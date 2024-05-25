package com.example.storyappdicoding.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.storyappdicoding.data.local.pref.UserModel
import com.example.storyappdicoding.data.repository.UserRepository
import com.example.storyappdicoding.data.repository.StoryRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val storyRepository: StoryRepository,
    private val repository: UserRepository
) : ViewModel() {

    private val _refresh = MutableLiveData<Unit>()
    val refresh: LiveData<Unit> = _refresh

    init {
        refreshData()
    }

    fun getAllStory() = _refresh.switchMap {
        storyRepository.getAllStory()
    }

    fun refreshData() {
        _refresh.value = Unit
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.userLogout()
        }
    }
}