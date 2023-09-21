package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ItemRoomDatabase? = null
        // companion 객체 내에서 데이터베이스에 관한 null을 허용하는 비공개 변수 INSTANCE를 선언하고 null로 초기화합니다.
        // INSTANCE 변수는 데이터베이스가 만들어지면 데이터베이스 참조를 유지합니다.
        // 이를 통해 주어진 시점에 열린 데이터베이스의 단일 인스턴스를 유지할 수 있습니다. 데이터베이스는 만들고 유지하는 데 비용이 많이 듭니다.
        // @Volatile에 INSTANCE 주석을 답니다. 휘발성 변수의 값은 캐시되지 않고 모든 쓰기와 읽기는 기본 메모리에서 실행됩니다.
        // 이렇게 하면 INSTANCE 값이 항상 최신 상태로 유지되고 모든 실행 스레드에서 같은지 확인할 수 있습니다.
        // 즉, 한 스레드에서 INSTANCE를 변경하면 다른 모든 스레드에 즉시 표시됩니다.

        fun getDatabase(context: Context): ItemRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // 여러 스레드가 잠재적으로 경합 상태로 실행되고 동시에 데이터베이스 인스턴스를 요청하여
                // 하나가 아닌 두 개의 데이터베이스가 생성될 수 있습니다.
                // 코드를 래핑하여 synchronized 블록 내에 데이터베이스를 가져오면 한 번에 한 실행 스레드만
                // 이 코드 블록에 들어갈 수 있으므로 데이터베이스가 한 번만 초기화됩니다.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}