package com.example.fintesimal.data.network

import com.example.fintesimal.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T: Any> apiRequest(call: suspend () -> Response<T>) : T {
        val response = call.invoke()

        if(response.isSuccessful) {
            println("RESPONSE: ${response.body()}")
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch(e: JSONException) { }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")

            throw ApiException(message.toString())
        }
    }
}