package com.example.fintesimal.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fintesimal.data.repository.MapRepository

@Suppress("UNCHECKED_CAST")
class FirstViewModelFactory(
    private val repository: MapRepository,
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FirstViewModel(repository) as T
    }
}