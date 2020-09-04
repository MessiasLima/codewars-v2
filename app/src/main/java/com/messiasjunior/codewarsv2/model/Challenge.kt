package com.messiasjunior.codewarsv2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Challenge(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val tags: List<String>?,
    val url: String?,
    val createdBy: String?,
    val creatorUrl: String?
)

enum class ChallengeType {
    COMPLETED, AUTHORED
}
