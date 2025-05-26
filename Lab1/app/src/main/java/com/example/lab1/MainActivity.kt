package com.example.lab1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editName = findViewById(R.id.editName)
        resultText = findViewById(R.id.textResult)

        val checkPepperoni = findViewById<CheckBox>(R.id.checkPepperoni)
        val checkMargarita = findViewById<CheckBox>(R.id.checkMargarita)
        val checkHawaiian = findViewById<CheckBox>(R.id.checkHawaiian)

        val checkSmall = findViewById<CheckBox>(R.id.checkSmall)
        val checkMedium = findViewById<CheckBox>(R.id.checkMedium)
        val checkLarge = findViewById<CheckBox>(R.id.checkLarge)

        val buttonOk = findViewById<Button>(R.id.buttonOk)

        buttonOk.setOnClickListener {
            val name = editName.text.toString().trim()
            val types = listOf(checkPepperoni, checkMargarita, checkHawaiian).filter { it.isChecked }
            val sizes = listOf(checkSmall, checkMedium, checkLarge).filter { it.isChecked }

            if (name.isEmpty() || types.isEmpty() || sizes.isEmpty()) {
                Toast.makeText(this, "Будь ласка, заповніть усі дані!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val typeList = types.joinToString { it.text }
            val sizeList = sizes.joinToString { it.text }

            val result = """
                Замовлення для: $name
                Тип піци: $typeList
                Розмір: $sizeList
            """.trimIndent()

            resultText.text = result
        }
    }
}
