package me.ex1tus.feed.navigation.domain

data class Navigation(
    val prev: Int,
    val next: Int,
    val hasPrev: Boolean,
    val hasNext: Boolean
)