package com.example.memo_line.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memo_line.data.Memo

@Dao
interface MemoDao {

    @Query("SELECT * FROM Memos") fun getMemos(): List<Memo>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertMemo(task: Memo)

}