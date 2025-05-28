package com.example.lab6

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class AllCountriesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_countries)

        recyclerView = findViewById(R.id.recyclerViewAll)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchAllCountries()
    }

    private fun fetchAllCountries() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(RestCountriesApi::class.java)
        api.getAllCountries().enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful && response.body() != null) {
                    recyclerView.adapter = CountryAdapter(response.body()!!)
                } else {
                    Toast.makeText(this@AllCountriesActivity, "Помилка завантаження", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Toast.makeText(this@AllCountriesActivity, "Помилка: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
