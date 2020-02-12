package com.example.memo_line.data.source

import com.example.memo_line.data.Memo

interface MemosDataSource {

    interface LoadMemosCallback {

//        fun onTasksLoaded(tasks: List<Task>)
//
//        fun onDataNotAvailable()
    }

    interface GetMemoCallback {

//        fun onTaskLoaded(task: Task)
//
//        fun onDataNotAvailable()
    }

//    fun getTasks(callback: LoadTasksCallback)
//
//    fun getTask(taskId: String, callback: GetTaskCallback)
//
    fun saveMemo(memo: Memo)
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