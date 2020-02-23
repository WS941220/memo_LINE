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

    /**
     * 메모리스트 가져옴
     */
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

    /**
     * 메모 추가
     */
    override fun insertMemo(memo: Memo) {
        executors.diskIO.execute { memoDao.insertMemo(memo) }

        // Do in memory cache update to keep the app UI up to date
        if (cachedMemos == null) {
            cachedMemos = LinkedHashMap<String, Memo>()
        }
        cachedMemos.put(memo.id, memo)
    }

    /**
     * 전체 선택
     */
    override fun checkAllMemo() {
        executors.diskIO.execute {memoDao.updateChecked(true)}
    }

    /**
     * 전체 선택 취소
     */
    override fun cancelAllMemo() {
        executors.diskIO.execute {memoDao.updateChecked(false)}
    }

    /**
     * 메모 선택
     */
    override fun checkedMemo(checkedMemo: Memo) {
        cacheAndPerform(checkedMemo) {
            it.isChecked = true
            executors.diskIO.execute {memoDao.updateChecked(checkedMemo.id, true)}
        }
    }

    /**
     * 메모 선택 취소
     */
    override fun canceledMemo(canceledMemo: Memo) {
        cacheAndPerform(canceledMemo) {
            it.isChecked = false
            executors.diskIO.execute {memoDao.updateChecked(canceledMemo.id, false)}
        }
    }

    /**
     * 선택메모 삭제
     */
    override fun deleteCheckedMemos() {
        cachedMemos = cachedMemos.filterValues {
            !it.isChecked
        } as LinkedHashMap<String, Memo>
        executors.diskIO.execute {memoDao.deleteCheckedMemos() }
    }

    /**
     * 메모 삭제
     */
    override fun deleteMemo(memoId: String) {
        executors.diskIO.execute { memoDao.deleteMemoById(memoId) }
        cachedMemos.remove(memoId)

    }

    override fun refreshMemos() {
        cacheIsDirty = false
    }

    /**
     * 메모 가져옴
     */
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

    private inline fun cacheAndPerform(memo: Memo, perform: (Memo) -> Unit) {
        val cachedMemo = Memo(memo.title, memo.content, memo.image, memo.id).apply {
            isChecked = memo.isChecked
        }
        cachedMemos.put(cachedMemo.id, cachedMemo)
        perform(cachedMemo)
    }

}