package com.example.lab6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CountryAdapter(private val countries: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flag: ImageView = view.findViewById(R.id.imageFlag)
        val name: TextView = view.findViewById(R.id.textName)
        val capital: TextView = view.findViewById(R.id.textCapital)
        val region: TextView = view.findViewById(R.id.textRegion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.name.text = country.name.common
        holder.capital.text = "Столиця: ${country.capital?.getOrNull(0) ?: "—"}"
        holder.region.text = "Регіон: ${country.region}"

        Glide.with(holder.flag.context)
            .load(country.flags.png)
            .into(holder.flag)
    }

    override fun getItemCount() = countries.size
}
