package com.cynapus.app.ui.memo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cynapus.app.data.database.AppDatabase
import com.cynapus.app.data.entity.MemoEntity
import com.cynapus.app.repository.MemoRepository
import kotlinx.coroutines.launch
import java.util.Date

class MemoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MemoRepository
    val allMemos: LiveData<List<MemoEntity>>

    init {
        val memoDao = AppDatabase.getDatabase(application).memoDao()
        repository = MemoRepository(memoDao)
        allMemos = repository.getAllMemos()
    }

    fun insertMemo(title: String, content: String) = viewModelScope.launch {
        val memo = MemoEntity(
            title = title,
            content = content,
            createdAt = Date(),
            updatedAt = Date()
        )
        repository.insertMemo(memo)
    }

    fun updateMemo(memo: MemoEntity, title: String, content: String) = viewModelScope.launch {
        val updatedMemo = memo.copy(
            title = title,
            content = content,
            updatedAt = Date()
        )
        repository.updateMemo(updatedMemo)
    }

    fun deleteMemo(memo: MemoEntity) = viewModelScope.launch {
        repository.deleteMemo(memo)
    }
}