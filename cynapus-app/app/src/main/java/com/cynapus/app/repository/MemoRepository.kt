package com.cynapus.app.repository

import androidx.lifecycle.LiveData
import com.cynapus.app.data.dao.MemoDao
import com.cynapus.app.data.entity.MemoEntity

class MemoRepository(private val memoDao: MemoDao) {
    fun getAllMemos(): LiveData<List<MemoEntity>> = memoDao.getAllMemos()

    suspend fun getMemoById(id: Long): MemoEntity? = memoDao.getMemoById(id)

    suspend fun insertMemo(memo: MemoEntity): Long = memoDao.insertMemo(memo)

    suspend fun updateMemo(memo: MemoEntity) = memoDao.updateMemo(memo)

    suspend fun deleteMemo(memo: MemoEntity) = memoDao.deleteMemo(memo)

    suspend fun deleteMemoById(id: Long) = memoDao.deleteMemoById(id)
}