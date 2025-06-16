package com.cynapus.app.repository

import androidx.lifecycle.LiveData
import com.cynapus.app.data.dao.TodoDao
import com.cynapus.app.data.entity.TodoEntity

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllTodos(): LiveData<List<TodoEntity>> = todoDao.getAllTodos()

    suspend fun getTodoById(id: Long): TodoEntity? = todoDao.getTodoById(id)

    suspend fun insertTodo(todo: TodoEntity): Long = todoDao.insertTodo(todo)

    suspend fun updateTodo(todo: TodoEntity) = todoDao.updateTodo(todo)

    suspend fun deleteTodo(todo: TodoEntity) = todoDao.deleteTodo(todo)

    suspend fun deleteTodoById(id: Long) = todoDao.deleteTodoById(id)
}