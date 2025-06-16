package com.cynapus.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cynapus.app.databinding.ActivityMainBinding
import com.cynapus.app.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.todo_tab)
                1 -> getString(R.string.memo_tab)
                else -> ""
            }
        }.attach()
    }
}