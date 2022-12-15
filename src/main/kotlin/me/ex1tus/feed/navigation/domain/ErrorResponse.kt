package me.ex1tus.feed.navigation.domain

data class ErrorResponse(
    val code: ErrorCode,
    val description: String
)