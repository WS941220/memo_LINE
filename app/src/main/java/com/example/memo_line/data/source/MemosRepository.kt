package com.example.memo_line.data.source

import com.example.android.architecture.blueprints.todoapp.util.AppExecutors
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.local.MemoDao
import com.example.memo_line.data.source.local.MemoDatabase
import com.example.memo_line.di.Scoped.AppScoped
import java.util.*
import javax.inject.Inject

@AppScoped
class MemosRepository @Inject constructor(
    private var executors: AppExecutors,
    private var memoDao: MemoDao
) : MemosDataSource {

    var cachedMemos: LinkedHashMap<String, Memo> = LinkedHashMap()
    var cacheIsDirty = true

    override fun getMemos(callback: MemosDataSource.LoadMemosCallback) {

        if (cachedMemos.isNotEmpty() && cacheIsDirty) {
            callback.onMemosLoaded(ArrayList(cachedMemos.values))
            return
        }
        if (!cacheIsDirty) {
            val runnable = Runnable {
                val memos: List<Memo> = memoDao.getMemos()
                executors.mainThread.execute(Runnable {
                    callback.onMemosLoaded(memos)
                })
            }
            executors.diskIO.execute(runnable)
        }
    }

    override fun insertMemo(memo: Memo) {
        executors.diskIO.execute { memoDao.insertMemo(memo) }

        // Do in memory cache update to keep the app UI up to date
        if (cachedMemos == null) {
            cachedMemos = LinkedHashMap<String, Memo>()
        }
        cachedMemos.put(memo.id, memo)
    }

    override fun deleteMemo(memoId: String) {
        executors.diskIO.execute { memoDao.deleteMemoById(memoId)}
        cachedMemos.remove(memoId)
    }

    override fun deleteMemos(memos: List<String>) {
        executors.diskIO.execute { memoDao.deleteMemos(memos)}
        for (i in 0..memos.size - 1) {
            cachedMemos.remove(memos[i])
        }
    }

    override fun refreshMemos() {
        cacheIsDirty = false
    }

    override fun getMemo(memoId: String, callback: MemosDataSource.GetMemoCallback) {
        executors.diskIO.execute {
            val memo = memoDao.getMemoById(memoId)
            executors.mainThread.execute {
                if (memo != null) {
                    callback.onMemoLoaded(memo)
                }
            }
        }
    }

}