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

    @Query("SELECT * FROM memo WHERE entryid = :memoId") fun getMemoById(memoId: String): Memo?

    @Query("UPDATE memo SET checked = :checked") fun updateChecked(checked: Boolean)

    @Query("UPDATE memo SET checked = :checked WHERE entryid = :memoId") fun updateChecked(memoId: String, checked: Boolean)

    @Query("DELETE FROM memo WHERE entryid = :memoId") fun deleteMemoById(memoId: String): Int

    @Query("DELETE FROM memo where entryid in (:idList)") fun deleteMemos(idList: List<String>)

    @Query("DELETE FROM memo WHERE checked = 1") fun deleteCheckedMemos(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertMemo(memo: Memo)

}