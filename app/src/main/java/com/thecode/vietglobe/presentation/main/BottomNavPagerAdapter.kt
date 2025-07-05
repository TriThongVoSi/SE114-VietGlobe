package com.thecode.vietglobe.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thecode.vietglobe.presentation.main.bookmark.BookmarksFragment
import com.thecode.vietglobe.presentation.main.home.HomeFragment
import com.thecode.vietglobe.presentation.main.search.SearchFragment

class BottomNavPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            else -> BookmarksFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
