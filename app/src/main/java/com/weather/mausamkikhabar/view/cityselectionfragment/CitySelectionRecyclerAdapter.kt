package com.weather.mausamkikhabar.view.cityselectionfragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.weather.mausamkikhabar.data.model.City
import com.weather.mausamkikhabar.databinding.ItemCitySelectionBinding

/**
 * Created on 22-Jul-19.
 */
class CitySelectionRecyclerAdapter(var cityList: List<City>) :
    RecyclerView.Adapter<CitySelectionRecyclerAdapter.ViewHolder>(), Filterable {

    private lateinit var onCitySelectedListener: OnCitySelectedListener
    var filterCityList: List<City>? = null

    init {
        filterCityList = cityList
    }


    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filterCityList = cityList
                } else {
                    val filteredList = ArrayList<City>()
                    for (row in cityList) {
                        if (row.name!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filterCityList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterCityList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                cityList = results?.values as ArrayList<City>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemHouseBinding = ItemCitySelectionBinding.inflate(inflater, parent, false)
        return ViewHolder(itemHouseBinding)

    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.city = cityList[position]
        holder.mBinding.root.setOnClickListener {

            if (::onCitySelectedListener.isInitialized) {
                onCitySelectedListener.onCitySelected(cityList[position])
            }
        }
    }

    fun setOnCitySelectedListener(onCitySelectedListener: OnCitySelectedListener) {
        this.onCitySelectedListener = onCitySelectedListener
    }

    interface OnCitySelectedListener {

        fun onCitySelected(city: City)

    }

    class ViewHolder(var mBinding: ItemCitySelectionBinding) : RecyclerView.ViewHolder(mBinding.root)
}