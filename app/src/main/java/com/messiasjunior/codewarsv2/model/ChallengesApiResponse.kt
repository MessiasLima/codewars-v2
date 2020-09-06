package com.messiasjunior.codewarsv2.model

data class ChallengesApiResponse(
    val totalPages: Int? = 0,
    val totalItems: Int? = 0,
    val data: List<Challenge>
)
