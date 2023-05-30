package com.example.githubuserapplication.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserapplication.db.Favorites

class FavoritesDiffCallback (private val mBeforeFavoriteUserList: List<Favorites>, private val mCurrentFavoriteUserList: List<Favorites>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mBeforeFavoriteUserList.size
    }

    override fun getNewListSize(): Int {
        return mCurrentFavoriteUserList.size
    }

    override fun areItemsTheSame(beforePos: Int, currentPos: Int): Boolean {
        return mBeforeFavoriteUserList[beforePos].username == mCurrentFavoriteUserList[currentPos].username
    }

    override fun areContentsTheSame(beforePos: Int, currentPos: Int): Boolean {
        return mBeforeFavoriteUserList[beforePos].username == mCurrentFavoriteUserList[currentPos].username && mBeforeFavoriteUserList[beforePos].avatarUrl == mCurrentFavoriteUserList[currentPos].avatarUrl
    }
}