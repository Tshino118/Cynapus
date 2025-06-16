package com.cynapus.app.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cynapus.app.R
import com.cynapus.app.databinding.DialogAddTodoBinding
import com.cynapus.app.databinding.FragmentTodoBinding
import com.cynapus.app.ui.adapter.TodoAdapter

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        
        setupRecyclerView()
        setupFab()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            onTodoClick = { todo ->
                // TODO: Implement edit todo functionality
            },
            onDeleteClick = { todo ->
                todoViewModel.deleteTodo(todo)
            },
            onCompletionToggle = { todo ->
                todoViewModel.toggleTodoCompletion(todo)
            }
        )
        
        binding.recyclerViewTodos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

    private fun setupFab() {
        binding.fabAddTodo.setOnClickListener {
            showAddTodoDialog()
        }
    }

    private fun showAddTodoDialog() {
        val dialogBinding = DialogAddTodoBinding.inflate(layoutInflater)
        
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_todo)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.save) { _, _ ->
                val title = dialogBinding.editTextTitle.text.toString().trim()
                val description = dialogBinding.editTextDescription.text.toString().trim()
                
                if (title.isNotEmpty()) {
                    todoViewModel.insertTodo(title, description)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun observeViewModel() {
        todoViewModel.allTodos.observe(viewLifecycleOwner) { todos ->
            todoAdapter.submitList(todos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}