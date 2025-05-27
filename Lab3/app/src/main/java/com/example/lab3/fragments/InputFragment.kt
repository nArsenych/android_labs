package com.example.lab3.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.lab3.R
import com.example.lab3.ResultActivity
import com.example.lab3.OrderDatabaseHelper

class InputFragment : Fragment() {

    interface OnOrderConfirmedListener {
        fun onOrderConfirmed(name: String, types: List<String>, sizes: List<String>)
    }

    private var listener: OnOrderConfirmedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOrderConfirmedListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val editName = view.findViewById<EditText>(R.id.editName)

        val checkPepperoni = view.findViewById<CheckBox>(R.id.checkPepperoni)
        val checkMargarita = view.findViewById<CheckBox>(R.id.checkMargarita)
        val checkHawaiian = view.findViewById<CheckBox>(R.id.checkHawaiian)

        val checkSmall = view.findViewById<CheckBox>(R.id.checkSmall)
        val checkMedium = view.findViewById<CheckBox>(R.id.checkMedium)
        val checkLarge = view.findViewById<CheckBox>(R.id.checkLarge)

        val buttonOk = view.findViewById<Button>(R.id.buttonOk)
        val buttonOpen = view.findViewById<Button>(R.id.buttonOpen)

        buttonOpen.setOnClickListener {
            val intent = Intent(requireContext(), ResultActivity::class.java)
            startActivity(intent)
        }

        buttonOk.setOnClickListener {
            val name = editName.text.toString().trim()
            val types = listOf(checkPepperoni, checkMargarita, checkHawaiian)
                .filter { it.isChecked }
                .map { it.text.toString() }

            val sizes = listOf(checkSmall, checkMedium, checkLarge)
                .filter { it.isChecked }
                .map { it.text.toString() }

            if (name.isEmpty() || types.isEmpty() || sizes.isEmpty()) {
                Toast.makeText(requireContext(), "Будь ласка, заповніть усі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = OrderDatabaseHelper(requireContext())
            val success = db.insertOrder(name, types.joinToString(), sizes.joinToString())

            if (success) {
                Toast.makeText(requireContext(), "Замовлення збережено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Помилка збереження", Toast.LENGTH_SHORT).show()
            }

            listener?.onOrderConfirmed(name, types, sizes)
        }

        return view
    }
}
