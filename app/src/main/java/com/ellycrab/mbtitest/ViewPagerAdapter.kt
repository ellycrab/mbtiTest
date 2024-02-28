package com.ellycrab.mbtitest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        //각 설문지 페이지는 총 4개
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment.newInstance(position)
    }
}