package com.messiasjunior.codewarsv2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Language(
    val name: String,
    val rank: Rank
) : Parcelable
