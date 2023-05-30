package com.example.githubuserapplication.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapplication.ItemsItem
import com.example.githubuserapplication.databinding.FragmentTabLayoutBinding

class TabLayoutFragment : Fragment() {
    private lateinit var binding: FragmentTabLayoutBinding
    private val detailViewModel: DetailViewModel by activityViewModels()

    private var username: String? = null
    private var pos: Int? = null

    companion object {
        const val ARG_USERNAME = "param1"
        const val ARG_POSITION = "param2"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
            pos = it.getInt(ARG_POSITION)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFragmentTab.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFragmentTab.addItemDecoration(itemDecoration)
        binding.rvFragmentTab.setHasFixedSize(true)

        if (pos != 1){
            detailViewModel.getFollowing(username!!)
            detailViewModel.following.observe(viewLifecycleOwner){listFollow -> setListFollowersData(listFollow)}
            detailViewModel.isFollowLoading.observe(viewLifecycleOwner, {pageOnLoadingTab(it)})
        } else {
            detailViewModel.getFollowers(username!!)
            detailViewModel.followers.observe(viewLifecycleOwner){listFollow -> setListFollowersData(listFollow)}
            detailViewModel.isFollowLoading.observe(viewLifecycleOwner, {pageOnLoadingTab(it)})
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setListFollowersData(followers: List<ItemsItem>) {
        val followersAdapter = FollowersAdapter(followers)
        binding.rvFragmentTab.adapter = followersAdapter
    }

    private fun pageOnLoadingTab(isLoading: Boolean) {
        binding.progressBarTabLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}