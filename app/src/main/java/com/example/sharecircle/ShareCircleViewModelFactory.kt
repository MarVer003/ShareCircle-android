package com.example.sharecircle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sharecircle.data.MyRepository

class ShareCircleViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShareCircleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShareCircleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}