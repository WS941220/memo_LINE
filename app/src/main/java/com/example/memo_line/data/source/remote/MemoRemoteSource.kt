package com.example.memo_line.data.source.remote

import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource

object TasksRemoteDataSource : MemosDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 5000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, Memo>(2)

    override fun saveMemo(memo: Memo) {
        TASKS_SERVICE_DATA.put(memo.id, memo)
    }

}