package com.example.kotlin_project_structures_views.Network

// api/ApiService.kt
import com.example.kotlin_project_structures_views.Model.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}
