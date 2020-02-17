package com.example.memo_line.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "memo")
data class Memo @JvmOverloads constructor(
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()
) {

    /**
     * True if the task is completed, false if it's active.
     */
    @ColumnInfo(name = "completed") var isCompleted = false

    val titleForList: String
        get() = if (title.isNotEmpty()) title else content

    val isActive
        get() = !isCompleted

    val isEmpty
        get() = title.isEmpty() && content.isEmpty()
}