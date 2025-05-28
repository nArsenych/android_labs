package com.example.lab6

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestCountriesApi {
    @GET("v3.1/name/{country}")
    fun getCountryByName(@Path("country") name: String): Call<List<Country>>

    @GET("v3.1/all")
    fun getAllCountries(): Call<List<Country>>
}