package com.example.memo_line.data.source

import androidx.lifecycle.LiveData
import com.example.memo_line.data.Memo
import javax.inject.Inject

interface MemosDataSource {

    fun getMemos(): LiveData<ArrayList<Memo>>
//
//    fun getTask(taskId: String, callback: GetTaskCallback)
//
    fun insertMemo(memo: Memo)
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
//    fun refreshTasks()
//
//    fun deleteAllTasks()
//
//    fun deleteTask(taskId: String)
}