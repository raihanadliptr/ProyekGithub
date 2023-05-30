package com.example.githubuserapplication.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorites(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null,
) : Parcelable