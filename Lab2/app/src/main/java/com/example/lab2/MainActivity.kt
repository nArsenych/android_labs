package com.example.lab2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2.fragments.InputFragment
import com.example.lab2.fragments.ResultFragment

class MainActivity : AppCompatActivity(), InputFragment.OnOrderConfirmedListener, ResultFragment.OnCancelListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, InputFragment())
            .commit()
    }

    override fun onOrderConfirmed(name: String, types: List<String>, sizes: List<String>) {
        val resultFragment = ResultFragment.newInstance(name, types, sizes)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, resultFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCancel() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, InputFragment())
            .commit()
    }
}
