package com.example.kotlin_project_structures_views.ViewModel

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_project_structures_views.Model.Post
import com.example.kotlin_project_structures_views.Network.ApiClient
import com.example.kotlin_project_structures_views.Repository.HomeRepository
import com.example.kotlin_project_structures_views.Repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private  val apiService = ApiClient.apiService
    private val repository = PostRepository(apiService);



    private val _posts = MutableStateFlow<List<Post>>(emptyList()) ;
    val posts: StateFlow<List<Post>> get() = _posts ;

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error ;

    val loadingPosts = MutableLiveData<Boolean>()


    fun fetchPosts(context: Context) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            _error.value = "No internet connection"
            return
        }

        viewModelScope.launch {
            try {
                loadingPosts.value = true ;
                val response = repository.getPosts()
                if (response.isSuccessful) {
                    _posts.value = response.body() ?: emptyList()
                    println("checking response ${response.body()}")
                    loadingPosts.value = false ;
                } else {
                    _error.value = "Error: ${response.code()}"
                    loadingPosts.value = false ;
                }
            } catch (e: Exception) {
                loadingPosts.value = false ;
                _error.value = "Error: ${e.message}"
            }
        }

    }

}