package com.example.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import com.example.inventory.data.getFormattedPrice
import com.example.inventory.databinding.ItemListItemBinding

class ItemListAdapter(
//    private val inflater: LayoutInflater,
    private val onItemClicked: (Item) -> Unit
) : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val allItems = arrayListOf<Item>()

    fun updateList(newList: List<Item>) {
        allItems.clear()
        allItems.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // 굳이 layoutinflater를 가지고 올 필요가 없는 거?
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = allItems.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder) {
            with(allItems[position]) {
                binding.itemName.text = this.itemName
                binding.itemPrice.text = this.getFormattedPrice()
                binding.itemQuantity.text = this.quantityInStock.toString()
            }
            this.itemView.setOnClickListener {
                onItemClicked(allItems[position])
            }
        }
    }

}