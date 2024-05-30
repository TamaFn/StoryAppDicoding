package com.example.storyappdicoding.data.remote.retrofit

import com.example.storyappdicoding.data.remote.response.DetailResponse
import com.example.storyappdicoding.data.remote.response.LoginResponse
import com.example.storyappdicoding.data.remote.response.RegisterResponse
import com.example.storyappdicoding.data.remote.response.StoryResponse
import com.example.storyappdicoding.data.remote.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse


    @FormUrlEncoded
    @POST("register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("stories")
    suspend fun listStory(
        @Header("Authorization") token: String,
//      Untuk menentukan halaman data yang diinginkan.
        @Query("page") page: Int = 1,
//      Untuk menentukan jumlah item per halaman.
        @Query("size") size: Int = 30,
//      Untuk menentukan lokasi maps
        @Query("location") location: Int = 0
    ) : StoryResponse


    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): UploadResponse

    @GET("stories/{id}")
    suspend fun detailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DetailResponse
}