package com.example.kotlin_project_structures_views.Repository

import com.example.kotlin_project_structures_views.Network.ApiService

class PostRepository(private val apiService: ApiService) {

    suspend fun getPosts() = apiService.getPosts() ;
}