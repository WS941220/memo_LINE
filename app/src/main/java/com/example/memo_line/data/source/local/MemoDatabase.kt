package com.example.memo_line.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.memo_line.data.Memo

@Database(entities = [Memo::class], version = 1)
@TypeConverters(Converters::class)
abstract class MemoDatabase : RoomDatabase() {

    abstract fun memoDao(): MemoDao

}