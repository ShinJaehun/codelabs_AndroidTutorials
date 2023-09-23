package com.example.inventory

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems : LiveData<List<Item>> = itemDao.getItems().asLiveData()

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData() // Flow 값을 LiveData로 사용하려면!
    }

    fun sellItem(item: Item) {
        if (item.quantityInStock > 0) {
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            // copy() 함수는 기본적으로 데이터 클래스의 모든 인스턴스에 제공됩니다.
            // 이 함수는 일부 속성을 변경하지만 나머지 속성은 변경하지 않고 그대로 두기 위해
            // 객체를 복사하는 데 사용됩니다.
            updateItem(newItem)
        }
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) : Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        // 아... 이걸 어떻게 설명해야하나
        // private fun updateItem()과 별개로 존재 가능!
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(updatedItem) // 이렇게 사용까지 가능!
    }

    fun isStockAvailable(item: Item): Boolean {
        return (item.quantityInStock > 0)
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InventoryViewModel(itemDao) as T
//        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return InventoryViewModel(itemDao) as T
//        }
//        throw IllegalArgumentException("Unknown viewmodel class")
    }
}