package com.cynapus.app.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cynapus.app.data.entity.TodoEntity
import com.cynapus.app.databinding.ItemTodoBinding
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter(
    private val onTodoClick: (TodoEntity) -> Unit,
    private val onDeleteClick: (TodoEntity) -> Unit,
    private val onCompletionToggle: (TodoEntity) -> Unit
) : ListAdapter<TodoEntity, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoEntity) {
            binding.apply {
                textViewTitle.text = todo.title
                textViewDescription.text = todo.description
                textViewDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(todo.createdAt)
                
                checkBoxCompleted.isChecked = todo.isCompleted
                
                // Set strike-through effect for completed todos
                if (todo.isCompleted) {
                    textViewTitle.paintFlags = textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textViewDescription.paintFlags = textViewDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTitle.paintFlags = textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    textViewDescription.paintFlags = textViewDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                
                checkBoxCompleted.setOnCheckedChangeListener { _, _ ->
                    onCompletionToggle(todo)
                }
                
                buttonDelete.setOnClickListener {
                    onDeleteClick(todo)
                }
                
                root.setOnClickListener {
                    onTodoClick(todo)
                }
            }
        }
    }

    class TodoDiffCallback : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem == newItem
        }
    }
}