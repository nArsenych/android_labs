package com.example.lab6

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var editCountry: EditText
    private lateinit var textResult: TextView
    private lateinit var imageFlag: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCountry = findViewById(R.id.editCountry)
        textResult = findViewById(R.id.textResult)
        imageFlag = findViewById(R.id.imageFlag)

        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        val buttonAllCountries = findViewById<Button>(R.id.buttonAllCountries)

        buttonSearch.setOnClickListener {
            val name = editCountry.text.toString().trim()
            if (name.isNotEmpty()) {
                fetchCountry(name)
            } else {
                Toast.makeText(this, "Введіть назву країни", Toast.LENGTH_SHORT).show()
            }
        }

        buttonAllCountries.setOnClickListener {
            val intent = Intent(this, AllCountriesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchCountry(name: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(RestCountriesApi::class.java)
        api.getCountryByName(name).enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful && response.body() != null) {
                    val country = response.body()!![0]
                    val capital = country.capital?.getOrNull(0) ?: "Немає даних"
                    val info = """
                        Назва: ${country.name.common}
                        Столиця: $capital
                        Населення: ${country.population}
                        Регіон: ${country.region}
                    """.trimIndent()

                    textResult.text = info
                    Glide.with(this@MainActivity)
                        .load(country.flags.png)
                        .into(imageFlag)
                } else {
                    textResult.text = "Країну не знайдено"
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                textResult.text = "Помилка: ${t.message}"
            }
        })
    }
}
