package com.cynapus.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cynapus.app.data.entity.MemoEntity
import com.cynapus.app.databinding.ItemMemoBinding
import java.text.SimpleDateFormat
import java.util.*

class MemoAdapter(
    private val onMemoClick: (MemoEntity) -> Unit,
    private val onDeleteClick: (MemoEntity) -> Unit
) : ListAdapter<MemoEntity, MemoAdapter.MemoViewHolder>(MemoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MemoViewHolder(private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: MemoEntity) {
            binding.apply {
                textViewTitle.text = memo.title
                textViewContent.text = memo.content
                textViewDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(memo.createdAt)
                
                buttonDelete.setOnClickListener {
                    onDeleteClick(memo)
                }
                
                root.setOnClickListener {
                    onMemoClick(memo)
                }
            }
        }
    }

    class MemoDiffCallback : DiffUtil.ItemCallback<MemoEntity>() {
        override fun areItemsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean {
            return oldItem == newItem
        }
    }
}