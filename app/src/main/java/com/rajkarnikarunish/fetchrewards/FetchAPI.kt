package com.rajkarnikarunish.fetchrewards

import retrofit2.Response
import retrofit2.http.GET

interface FetchAPI {
    
    @GET("/hiring.json")
    suspend fun getItems(): List<Item>
}