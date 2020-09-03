package com.messiasjunior.codewarsv2.model

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Language(
    val name: String,
    @Embedded
    val rank: Rank
) : Parcelable
