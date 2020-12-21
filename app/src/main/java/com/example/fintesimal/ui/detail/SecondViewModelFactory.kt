package com.example.fintesimal.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fintesimal.data.repository.MapRepository

@Suppress("UNCHECKED_CAST")
class SecondViewModelFactory(
    private val repository: MapRepository,
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SecondViewModel(repository) as T
    }
}