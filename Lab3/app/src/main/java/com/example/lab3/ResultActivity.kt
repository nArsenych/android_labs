package com.example.lab3

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val listView = findViewById<ListView>(R.id.orderList)
        val emptyText = findViewById<TextView>(R.id.emptyText)

        val db = OrderDatabaseHelper(this)
        val orders = db.getAllOrders()

        if (orders.isEmpty()) {
            listView.visibility = ListView.GONE
            emptyText.visibility = TextView.VISIBLE
        } else {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, orders)
            listView.adapter = adapter
            listView.visibility = ListView.VISIBLE
            emptyText.visibility = TextView.GONE
        }
    }
}
