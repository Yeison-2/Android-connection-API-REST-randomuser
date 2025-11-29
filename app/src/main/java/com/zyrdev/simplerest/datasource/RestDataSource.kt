package com.zyrdev.simplerest.datasource

import com.zyrdev.simplerest.model.ApiResponse
import retrofit2.http.GET

interface RestDataSource {

    @GET("?inc=name,location,picture")
    suspend fun getUser(): ApiResponse
}



