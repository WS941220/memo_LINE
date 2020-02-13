package com.example.memo_line.data.source.local

import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource

class TasksLocalDataSource private constructor(
    val memoDao: MemoDao
) : MemosDataSource {

    override fun saveMemo(memo: Memo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
