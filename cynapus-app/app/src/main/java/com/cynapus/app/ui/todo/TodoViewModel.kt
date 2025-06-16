package com.cynapus.app.ui.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cynapus.app.data.database.AppDatabase
import com.cynapus.app.data.entity.TodoEntity
import com.cynapus.app.repository.TodoRepository
import kotlinx.coroutines.launch
import java.util.Date

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository
    val allTodos: LiveData<List<TodoEntity>>

    init {
        val todoDao = AppDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        allTodos = repository.getAllTodos()
    }

    fun insertTodo(title: String, description: String) = viewModelScope.launch {
        val todo = TodoEntity(
            title = title,
            description = description,
            createdAt = Date(),
            updatedAt = Date()
        )
        repository.insertTodo(todo)
    }

    fun updateTodo(todo: TodoEntity) = viewModelScope.launch {
        val updatedTodo = todo.copy(updatedAt = Date())
        repository.updateTodo(updatedTodo)
    }

    fun deleteTodo(todo: TodoEntity) = viewModelScope.launch {
        repository.deleteTodo(todo)
    }

    fun toggleTodoCompletion(todo: TodoEntity) = viewModelScope.launch {
        val updatedTodo = todo.copy(
            isCompleted = !todo.isCompleted,
            updatedAt = Date()
        )
        repository.updateTodo(updatedTodo)
    }
}