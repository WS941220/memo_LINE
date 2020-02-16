package com.example.memo_line.data.source

import com.example.android.architecture.blueprints.todoapp.util.AppExecutors
import com.example.memo_line.data.Memo
import com.example.memo_line.data.source.local.MemoDao
import com.example.memo_line.di.Scoped.AppScoped
import javax.inject.Inject

@AppScoped
class MemosRepository @Inject constructor(private var executors: AppExecutors, private var memoDao: MemoDao) : MemosDataSource {

    override fun getMemos(callback: MemosDataSource.LoadMemosCallback) {
//        return memoDao.getMemos()
        val runnable = Runnable {
            val memos: List<Memo> = memoDao.getMemos()
            executors.mainThread.execute(Runnable {
                if (memos.isEmpty()) {
                    callback.onMemoLoaded(memos)
                } else {
                    callback.onDataNotAvailable()
                }
            })
        }
        executors.diskIO.execute(runnable)
    }

    override fun insertMemo(memo: Memo) {
        memoDao.insertMemo(memo)
    }

}