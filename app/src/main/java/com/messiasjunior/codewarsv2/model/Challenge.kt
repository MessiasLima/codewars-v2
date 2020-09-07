package com.messiasjunior.codewarsv2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(indices = [Index(value = ["codewarsID"], unique = true)])
@Parcelize
data class Challenge(
    @PrimaryKey(autoGenerate = true)
    val _id: Long?,
    @SerializedName("id")
    val codewarsID: String,
    val name: String?,
    val description: String?,
    val tags: List<String>?,
    val url: String?,
    val creatorUsername: String?,
    val creatorUrl: String?
) : Parcelable

enum class ChallengeType {
    COMPLETED, AUTHORED
}
