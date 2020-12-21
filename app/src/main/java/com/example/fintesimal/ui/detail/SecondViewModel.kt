package com.example.fintesimal.ui.detail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.fintesimal.R
import com.example.fintesimal.data.db.entities.Address
import com.example.fintesimal.data.repository.MapRepository
import com.example.fintesimal.util.Coroutines

class SecondViewModel(
    private val repository: MapRepository
) : ViewModel() {
    var listener: SecondListener? = null
    lateinit var address: Address
    fun onReceiveData(idAddress: Int){
        Coroutines.io {
            val localAddress = repository.getAddressById(idAddress)
            address = localAddress
            listener?.onSuccess(address)
        }
    }

    fun onVisitClicked(view: View){
        Coroutines.io {
            address.visited = true
            repository.setAddress(listOf(address))
            onBackStack(view)
        }
    }

    fun onBackStack(view: View){
        Coroutines.main {
            Navigation.findNavController(view).navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    fun onMapReady(){
        listener?.onGoLocation(address)
    }
}