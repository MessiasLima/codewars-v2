package com.messiasjunior.codewarsv2.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey val username: String,
    val name: String?,
    val leaderboardPosition: Int?,
    val honor: Int?
) : Parcelable {
    @IgnoredOnParcel
    val displayName: String
        get() {
            return if (name.isNullOrBlank()) username else name
        }

    @Ignore
    @IgnoredOnParcel
    var ranks: Ranks? = null

    @IgnoredOnParcel
    @Embedded(prefix = "best_language_")
    var bestLanguage: Language? = null

    @IgnoredOnParcel
    var searchDate: Long? = null
}
