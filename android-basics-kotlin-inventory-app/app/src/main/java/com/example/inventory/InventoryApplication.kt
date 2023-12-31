/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.inventory

import android.app.Application
import com.example.inventory.data.ItemRoomDatabase

class InventoryApplication : Application() {
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
    // lazy 위임을 사용하므로 참조가 처음 필요하거나 처음 참조에 액세스할 때(앱이 시작될 때가 아니라)
    // database 인스턴스가 느리게 만들어집니다.
    // 이렇게 하면 처음 액세스할 때 데이터베이스(디스크의 물리적 데이터베이스)가 만들어집니다.
}
