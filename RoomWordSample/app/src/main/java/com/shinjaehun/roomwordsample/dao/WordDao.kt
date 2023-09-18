package com.shinjaehun.roomwordsample.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shinjaehun.roomwordsample.entity.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("select * from word_table order by word asc")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("delete from word_table")
    suspend fun deleteAll()
}