package com.cynapus.app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cynapus.app.ui.memo.MemoFragment
import com.cynapus.app.ui.todo.TodoFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodoFragment()
            1 -> MemoFragment()
            else -> TodoFragment()
        }
    }
}