package com.messiasjunior.codewarsv2.model

import androidx.room.Entity

@Entity(primaryKeys = ["username", "challengeId"])
data class UserCompletedChallenge(
    val username: String,
    val challengeId: String
)
