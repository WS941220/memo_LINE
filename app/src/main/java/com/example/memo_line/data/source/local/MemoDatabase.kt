package com.example.memo_line.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memo_line.data.Memo

@Database(entities = [Memo::class], version = 1)
abstract class MemoDatabase : RoomDatabase() {

    abstract fun memoDao(): MemoDao

    companion object {

        private var INSTANCE: MemoDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): MemoDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MemoDatabase::class.java, "Memos.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}