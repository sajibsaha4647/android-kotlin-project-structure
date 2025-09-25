package com.example.kotlin_project_structures_views.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project_structures_views.Model.HomeResponseModel
import com.example.kotlin_project_structures_views.Repository.HomeRepository

class HomeViewModel : ViewModel(){
    private val repository = HomeRepository()

    private  val _items = MutableLiveData<List<HomeResponseModel>>()

    val items: MutableLiveData<List<HomeResponseModel>>
        get() = _items

    init {
        items.value = repository.getItems().toMutableList();

        println("this was list ${items.value}")
    }

    fun addItem(item: HomeResponseModel) {
        val currentList = _items.value.orEmpty().toMutableList()
        currentList.add(item)
        // Update LiveData with new list
        _items.value = currentList
    }

    fun removeItem(item: HomeResponseModel) {
        val currentList = items.value?.toMutableList() ?: return
        currentList.remove(item)
        items.value = currentList
    }

    // Update item by id
    fun updateItem(id: Int, newName: String) {
        val list = (_items.value ?: return).toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index != -1) {
            list[index] = list[index].copy(name = newName)
            _items.value = list
        }
    }

}