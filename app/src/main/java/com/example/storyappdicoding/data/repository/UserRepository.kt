package com.example.storyappdicoding.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.local.pref.UserModel
import com.example.storyappdicoding.data.local.pref.UserPreference
import com.example.storyappdicoding.data.remote.response.LoginResponse
import com.example.storyappdicoding.data.remote.response.RegisterResponse
import com.example.storyappdicoding.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun userLogout() {
        userPreference.logout()
    }

    fun userRegister(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        val result = try {
            val response = apiService.userRegister(name, email, password)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
        emit(result)
    }

    fun userLogin(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        val result = try {
            val response = apiService.userLogin(email, password)
            userPreference.saveSession(UserModel(email, response.loginResult.token, true))
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
        emit(result)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}