package com.cynapus.app.ui.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cynapus.app.R
import com.cynapus.app.databinding.DialogAddMemoBinding
import com.cynapus.app.databinding.FragmentMemoBinding
import com.cynapus.app.ui.adapter.MemoAdapter

class MemoFragment : Fragment() {
    private var _binding: FragmentMemoBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var memoViewModel: MemoViewModel
    private lateinit var memoAdapter: MemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        memoViewModel = ViewModelProvider(this)[MemoViewModel::class.java]
        
        setupRecyclerView()
        setupFab()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        memoAdapter = MemoAdapter(
            onMemoClick = { memo ->
                // TODO: Implement edit memo functionality
            },
            onDeleteClick = { memo ->
                memoViewModel.deleteMemo(memo)
            }
        )
        
        binding.recyclerViewMemos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = memoAdapter
        }
    }

    private fun setupFab() {
        binding.fabAddMemo.setOnClickListener {
            showAddMemoDialog()
        }
    }

    private fun showAddMemoDialog() {
        val dialogBinding = DialogAddMemoBinding.inflate(layoutInflater)
        
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_memo)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.save) { _, _ ->
                val title = dialogBinding.editTextTitle.text.toString().trim()
                val content = dialogBinding.editTextContent.text.toString().trim()
                
                if (title.isNotEmpty()) {
                    memoViewModel.insertMemo(title, content)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun observeViewModel() {
        memoViewModel.allMemos.observe(viewLifecycleOwner) { memos ->
            memoAdapter.submitList(memos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}