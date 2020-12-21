package com.example.fintesimal.data.repository

import com.example.fintesimal.data.db.AppDatabase
import com.example.fintesimal.data.db.entities.Address
import com.example.fintesimal.data.network.Api
import com.example.fintesimal.data.network.SafeApiRequest
import com.example.fintesimal.data.network.response.mapResponse

class MapRepository (
    private val api: Api,
    private val db: AppDatabase
): SafeApiRequest(){
    suspend fun getAddress(): List<Address>{
        val localAddress = db.getAddress().getAddress()
        if (localAddress.isNotEmpty()){
            return localAddress
        }else{
            val response = updateDb()
            response.let {
                val listOfAddress = ArrayList<Address>()
                for (element in it){
                    val address = Address(
                        element.streetName,
                        element.suburb,
                        element.visited,
                        element.location.latitude,
                        element.location.longitude)
                    listOfAddress.add(address)
                }
                setAddress(listOfAddress)
                return listOfAddress
            }
        }
    }
    private suspend fun updateDb(): List<mapResponse>{
        return apiRequest{ api.getData() }
    }
    fun setAddress(address: List<Address>) = db.getAddress().insert(address)

    fun getAddressById(id: Int): Address{
        val localAddress = db.getAddress().getAddressById(id)
        return localAddress
    }

}