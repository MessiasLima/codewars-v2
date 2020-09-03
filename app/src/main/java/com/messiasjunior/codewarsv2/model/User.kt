package com.messiasjunior.codewarsv2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val username: String,
    val name: String?,
    val leaderboardPosition: Int?,
    val honor: Int,
    val ranks: Ranks
) : Parcelable
