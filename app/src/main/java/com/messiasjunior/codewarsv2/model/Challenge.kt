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
    val description: String? = null,
    val tags: List<String>? = null,
    val url: String? = null,
    val creatorUsername: String? = null,
    val creatorUrl: String? = null
) : Parcelable

enum class ChallengeType {
    COMPLETED, AUTHORED
}
