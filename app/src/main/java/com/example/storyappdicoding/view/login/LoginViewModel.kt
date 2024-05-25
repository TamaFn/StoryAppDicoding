package com.example.storyappdicoding.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.remote.response.LoginResponse
import com.example.storyappdicoding.data.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun userLogin(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> {

        return userRepository.userLogin(email, password)

    }
}