package com.example.storyappdicoding.data.local.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)