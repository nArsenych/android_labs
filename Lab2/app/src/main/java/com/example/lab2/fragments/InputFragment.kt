package com.example.lab2.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.lab2.R

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val editName = view.findViewById<EditText>(R.id.editName)

        val typePepperoni = view.findViewById<CheckBox>(R.id.checkPepperoni)
        val typeMargarita = view.findViewById<CheckBox>(R.id.checkMargarita)
        val typeHawaiian = view.findViewById<CheckBox>(R.id.checkHawaiian)

        val sizeSmall = view.findViewById<CheckBox>(R.id.checkSmall)
        val sizeMedium = view.findViewById<CheckBox>(R.id.checkMedium)
        val sizeLarge = view.findViewById<CheckBox>(R.id.checkLarge)

        val buttonOk = view.findViewById<Button>(R.id.buttonOk)

        buttonOk.setOnClickListener {
            val name = editName.text.toString().trim()

            val types = listOf(typePepperoni, typeMargarita, typeHawaiian).filter { it.isChecked }.map { it.text.toString() }
            val sizes = listOf(sizeSmall, sizeMedium, sizeLarge).filter { it.isChecked }.map { it.text.toString() }

            if (name.isEmpty() || types.isEmpty() || sizes.isEmpty()) {
                Toast.makeText(requireContext(), "Будь ласка, заповніть усі дані!", Toast.LENGTH_SHORT).show()
            } else {
                listener?.onOrderConfirmed(name, types, sizes)
            }
        }

        return view
    }
}
