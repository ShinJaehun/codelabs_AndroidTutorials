package com.example.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import com.example.inventory.databinding.ItemListItemBinding

class ItemListAdapter(
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val allItems = arrayListOf<Item>()

    fun updateList(newList: List<Item>) {
        allItems.clear()
        allItems.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = allItems.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder) {
            with(allItems[position]) {
                binding.itemName.text = this.itemName
                binding.itemPrice.text = this.itemPrice.toString()
                binding.itemQuantity.text = this.quantityInStock.toString()
            }
        }
    }

}