package me.ex1tus.feed.navigation.repository

interface FeedRepository {

    fun findBetween(from: Int, to: Int): List<String>
}
