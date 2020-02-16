package com.example.memo_line.data.source

import androidx.lifecycle.LiveData
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.local.MemoDao
import java.util.*
import javax.inject.Inject

class MemosRepository @Inject constructor(private var memoDao: MemoDao) : MemosDataSource {

    override fun getMemos(): LiveData<ArrayList<Memo>> {
        return memoDao.getMemos()
    }

    override fun insertMemo(memo: Memo) {
        memoDao.insertMemo(memo)
    }

}