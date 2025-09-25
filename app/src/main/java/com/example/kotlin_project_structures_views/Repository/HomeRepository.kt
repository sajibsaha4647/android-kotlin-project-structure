package com.example.kotlin_project_structures_views.Repository


import com.example.kotlin_project_structures_views.Model.HomeResponseModel
import com.google.gson.Gson

class HomeRepository {

    // Sample JSON string
    private val sampleJson = """
        [
            {"id":1,"name":"Item 1"},
            {"id":2,"name":"Item 2"}
        ]
    """

    fun getItems(): List<HomeResponseModel> {
        val gson = Gson()
        val jsonList = gson.fromJson(sampleJson, Array<HomeResponseModel>::class.java).toList()
        println("checking here json lists: ${sampleJson}")
        return jsonList ;
    }
}