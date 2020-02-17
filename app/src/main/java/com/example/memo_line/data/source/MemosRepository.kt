package com.example.memo_line.data.source

import com.example.android.architecture.blueprints.todoapp.util.AppExecutors
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.local.MemoDao
import com.example.memo_line.di.Scoped.AppScoped
import java.util.*
import javax.inject.Inject

@AppScoped
class MemosRepository @Inject constructor(
    private var executors: AppExecutors,
    private var memoDao: MemoDao
) : MemosDataSource {

    var cachedMemos: LinkedHashMap<String, Memo> = LinkedHashMap()
    var cacheIsDirty = false

    override fun getMemos(callback: MemosDataSource.LoadMemosCallback) {

        if (cachedMemos.isNotEmpty() && !cacheIsDirty) {
            callback.onMemosLoaded(ArrayList(cachedMemos.values))
            return
        }
        if (cacheIsDirty) {
            val runnable = Runnable {
                val memos: List<Memo> = memoDao.getMemos()
                executors.mainThread.execute(Runnable {
                    refreshCache(memos)
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

    override fun refreshMemos() {
        cacheIsDirty = true
    }

    private fun refreshCache(tasks: List<Memo>) {
        cachedMemos.clear()
        tasks.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    private inline fun cacheAndPerform(task: Memo, perform: (Memo) -> Unit) {
        val cachedTask = Memo(task.title, task.content, task.id).apply {
            isCompleted = task.isCompleted
        }
        cachedMemos.put(cachedTask.id, cachedTask)
        perform(cachedTask)
    }

}