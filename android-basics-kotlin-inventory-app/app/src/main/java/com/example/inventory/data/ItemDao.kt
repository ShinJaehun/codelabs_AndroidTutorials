package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("select * from item where id = :id")
    fun getItem(id: Int): Flow<Item>
//    Flow나 LiveData를 반환 유형으로 사용하면 데이터베이스의 데이터가 변경될 때마다 알림을 받을 수 있습니다.
//    지속성 레이어에서 Flow를 사용하는 것이 좋습니다.
//    Room은 이 Flow를 자동으로 업데이트하므로 명시적으로 한 번만 데이터를 가져오면 됩니다.
//    이는 다음 Codelab에서 구현할 인벤토리 목록을 업데이트하는 데 유용합니다.
//    Flow 반환 유형으로 인해 Room은 백그라운드 스레드에서도 쿼리를 실행합니다.
//    이를 명시적으로 suspend 함수로 만들고 코루틴 범위 내에서 호출할 필요는 없습니다.

    @Query("select * from item order by name asc")
    fun getItems(): Flow<List<Item>>


}