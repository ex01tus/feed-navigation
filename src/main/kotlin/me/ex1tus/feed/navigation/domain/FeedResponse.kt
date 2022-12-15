package me.ex1tus.feed.navigation.domain

data class FeedResponse(
    val items: List<String>,
    val navigation: Navigation
) {
    override fun toString() = "FeedResponse(feed=[${items.first()}..${items.last()}], navigation=$navigation)"
}
