package com.messiasjunior.codewarsv2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Challenge(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val tags: List<String>?,
    val url: String?,
    val creatorUsername: String?,
    val creatorUrl: String?
) : Parcelable

enum class ChallengeType {
    COMPLETED, AUTHORED
}
