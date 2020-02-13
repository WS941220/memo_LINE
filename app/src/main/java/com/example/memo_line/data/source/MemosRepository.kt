package com.example.memo_line.data.source

import android.util.Log
import com.example.memo_line.data.Memo

class MemosRepository(
    val memosRemoteDataSource: MemosDataSource,
    val memoLocalDataSource: MemosDataSource
) : MemosDataSource {

    var cachedMemos: LinkedHashMap<String, Memo> = LinkedHashMap()

    private inline fun cacheAndPerform(memo: Memo, perform: (Memo) -> Unit) {
        val cachedMemo = Memo(memo.title, memo.content, memo.id).apply {
            isCompleted = memo.isCompleted
        }
        cachedMemos.put(cachedMemo.id, cachedMemo)
        perform(cachedMemo)
    }

    companion object {

        private var INSTANCE: MemosRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */
        @JvmStatic fun getInstance(memosRemoteDataSource: MemosDataSource,
                                   memoLocalDataSource: MemosDataSource): MemosRepository {
            return INSTANCE ?: MemosRepository(memosRemoteDataSource, memoLocalDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }


    override fun saveMemo(memo: Memo) {
        cacheAndPerform(memo) {
            memoLocalDataSource.saveMemo(it)
        }
    }

}