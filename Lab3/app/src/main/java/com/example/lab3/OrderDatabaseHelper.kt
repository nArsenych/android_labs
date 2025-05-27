package com.example.lab3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OrderDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "orders.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE orders (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                types TEXT,
                sizes TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS orders")
        onCreate(db)
    }

    fun insertOrder(name: String, types: String, sizes: String): Boolean {
        val values = ContentValues().apply {
            put("name", name)
            put("types", types)
            put("sizes", sizes)
        }
        return writableDatabase.insert("orders", null, values) != -1L
    }

    fun getAllOrders(): List<String> {
        val list = mutableListOf<String>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM orders", null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(1)
                val types = cursor.getString(2)
                val sizes = cursor.getString(3)
                list.add("Клієнт: $name\nТип: $types\nРозмір: $sizes")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun clearOrders(): Boolean {
        return writableDatabase.delete("orders", null, null) >= 0
    }
}
