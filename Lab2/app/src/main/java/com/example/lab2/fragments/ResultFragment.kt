package com.example.lab2.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.lab2.R

class ResultFragment : Fragment() {

    interface OnCancelListener {
        fun onCancel()
    }

    private var listener: OnCancelListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCancelListener) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        val resultText = view.findViewById<TextView>(R.id.resultText)
        val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)

        val name = arguments?.getString("name") ?: ""
        val types = arguments?.getStringArrayList("types") ?: arrayListOf()
        val sizes = arguments?.getStringArrayList("sizes") ?: arrayListOf()

        resultText.text = """
            Замовлення для: $name
            Тип піци: ${types.joinToString(", ")}
            Розмір: ${sizes.joinToString(", ")}
        """.trimIndent()

        buttonCancel.setOnClickListener {
            listener?.onCancel()
        }

        return view
    }

    companion object {
        fun newInstance(name: String, types: List<String>, sizes: List<String>): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putString("name", name)
            args.putStringArrayList("types", ArrayList(types))
            args.putStringArrayList("sizes", ArrayList(sizes))
            fragment.arguments = args
            return fragment
        }
    }
}
