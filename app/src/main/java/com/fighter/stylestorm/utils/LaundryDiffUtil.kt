package com.fighter.stylestorm.utils

import androidx.recyclerview.widget.DiffUtil

class LaundryDiffUtil(private val oldList: List<Int>, private val newList: List<Int>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        (oldList[oldItemPosition] == newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}