package com.example.todoapp.database

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_items")
    fun getAll(): List<TodoEntity>

    @Query("SELECT * FROM todo_items WHERE title LIKE :title")
    fun update(title: String): TodoEntity

    @Insert
    fun insertAll(vararg todo: TodoEntity)

    @Delete
    fun delete(todo: TodoEntity)


    @Query("SELECT * FROM todo_items WHERE Id LIKE :todoId")
    fun deleteById(todoId: Int): TodoEntity



    @Update
    fun updateTodo(vararg todos: TodoEntity)

    //(onConflict = OnConflictStrategy.REPLACE)
}