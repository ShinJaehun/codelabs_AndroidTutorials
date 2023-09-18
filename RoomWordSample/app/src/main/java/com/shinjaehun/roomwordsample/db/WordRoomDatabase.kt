package com.shinjaehun.roomwordsample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shinjaehun.roomwordsample.dao.WordDao
import com.shinjaehun.roomwordsample.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = arrayOf(Word::class),
    version = 1,
    exportSchema = false
) abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        // DB seeds
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()
                    wordDao.deleteAll()
                    var word = Word("Hello")
                    wordDao.insert(word)
                    word = Word("World!")
                    wordDao.insert(word)

                    word = Word("ShinJaehun")
                    wordDao.insert(word)

                    word = Word("innovation!")
                    wordDao.insert(word)


                }
            }
        }
    }



    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) { // what is synchronized??
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                WordRoomDatabase::class.java,
                "word_database")
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance // 이게 return instance래...
            }
        }
    }
}