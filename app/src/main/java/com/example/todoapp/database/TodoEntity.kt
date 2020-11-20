package com.example.todoapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo_items")
class TodoEntity {

    @PrimaryKey(autoGenerate = true)
    var Id: Int =0

    @ColumnInfo (name ="title")
    var title:  String =""

}