package com.example.memo_line.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memo_line.data.Memo
import java.util.ArrayList

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo") fun getMemos(): List<Memo>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertMemo(memo: Memo)

}