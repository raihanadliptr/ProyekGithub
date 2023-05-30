package com.example.githubuserapplication.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = "raihan"

    override fun createFragment(pos: Int): Fragment {
        val fragment = TabLayoutFragment()
        fragment.arguments = Bundle().apply {
            putString(TabLayoutFragment.ARG_USERNAME, username)
            putInt(TabLayoutFragment.ARG_POSITION, pos + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}