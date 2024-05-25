package com.example.storyappdicoding.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.remote.response.RegisterResponse
import com.example.storyappdicoding.data.repository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> {
        return userRepository.userRegister(name, email, password)
    }
}