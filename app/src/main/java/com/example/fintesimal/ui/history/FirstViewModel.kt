package com.example.fintesimal.ui.history

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.fintesimal.R
import com.example.fintesimal.data.db.entities.Address
import com.example.fintesimal.data.repository.MapRepository
import com.example.fintesimal.util.Coroutines

class FirstViewModel(
    private val repository: MapRepository
) : ViewModel() {
    var listener: FirstListener? = null
    fun onLoad(){
        Coroutines.io {
            val data = repository.getAddress()
            listener?.onSuccess(data)
            onVisitCalculate(data)
        }
    }

    fun onShowDetail(view: View, address: Address){
        val bundle = bundleOf("idAddress" to address.id)
        Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    fun onVisitCalculate(arrayOfAddress: List<Address>){
        var visited = 0
        for (address in arrayOfAddress){
            if (address.visited)
                visited++
        }
        listener?.onVisitCalculate(visited)
    }
}