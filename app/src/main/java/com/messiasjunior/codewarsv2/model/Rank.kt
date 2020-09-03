package com.messiasjunior.codewarsv2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rank(
    val score: Int
) : Parcelable
