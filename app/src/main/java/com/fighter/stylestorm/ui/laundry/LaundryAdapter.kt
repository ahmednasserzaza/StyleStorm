package com.fighter.stylestorm.ui.laundry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.fighter.stylestorm.databinding.ItemLaundryBinding

class LaundryAdapter(private val dirtyClothes:List<Int>):RecyclerView.Adapter<LaundryAdapter.LaundryViewHolder>() {

    inner class LaundryViewHolder(val binding: ItemLaundryBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaundryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLaundryBinding.inflate(inflater , parent , false)
        return LaundryViewHolder(binding)
    }

    override fun getItemCount(): Int = dirtyClothes.size

    override fun onBindViewHolder(holder: LaundryViewHolder, position: Int) {
        val currentItem = dirtyClothes[position]
        Glide.with(holder.binding.root)
            .load(currentItem)
            .into(holder.binding.imageLaundryItem)
    }
}