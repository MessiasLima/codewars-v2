package com.messiasjunior.codewarsv2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ranks(
    val overall: Rank,
    val languages: Map<String, Rank>
) : Parcelable
