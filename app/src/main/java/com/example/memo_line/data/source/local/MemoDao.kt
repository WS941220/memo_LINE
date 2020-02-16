package com.example.memo_line.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memo_line.data.Memo

@Dao
interface MemoDao {

    @Query("SELECT * FROM memos") fun getMemos(): LiveData<ArrayList<Memo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertMemo(memo: Memo)

}