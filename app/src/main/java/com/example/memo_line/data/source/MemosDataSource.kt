package com.example.memo_line.data.source

import com.example.memo_line.data.Memo

interface MemosDataSource {

    interface LoadMemosCallback {
        fun onMemosLoaded(memos: List<Memo>)
        fun onDataNotAvailable()
    }

    fun getMemos(callback: LoadMemosCallback)

    fun insertMemo(memo: Memo)

    fun refreshMemos()

//
//    fun completeTask(task: Task)
//
//    fun completeTask(taskId: String)
//
//    fun activateTask(task: Task)
//
//    fun activateTask(taskId: String)
//
//    fun clearCompletedTasks()
//
//    fun deleteAllTasks()
//
//    fun deleteTask(taskId: String)
}