package com.example.memo_line.data.source.local

import androidx.annotation.VisibleForTesting
import com.example.android.architecture.blueprints.todoapp.util.AppExecutors
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.MemosDataSource

class MemoLocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val memoDao: MemoDao
) : MemosDataSource {

    companion object {
        private var INSTANCE: MemoLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, memoDao: MemoDao): MemoLocalDataSource {
            if (INSTANCE == null) {
                synchronized(MemoLocalDataSource::javaClass) {
                    INSTANCE = MemoLocalDataSource(appExecutors, memoDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

    override fun saveMemo(memo: Memo) {
        appExecutors.diskIO.execute { memoDao.insertMemo(memo) }
    }
}
