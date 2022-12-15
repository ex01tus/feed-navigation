package me.ex1tus.feed.navigation.service

import me.ex1tus.feed.navigation.domain.FeedResponse
import me.ex1tus.feed.navigation.domain.Navigation
import me.ex1tus.feed.navigation.repository.FeedRepository
import org.springframework.stereotype.Service

@Service
class FeedService(
    private val feedRepository: FeedRepository
) {

    fun getFeed(
        limit: Int,
        prev: Int?,
        next: Int?
    ) = prev?.let { getPrevious(limit, prev) }
        ?: next?.let { getNext(limit, next) }
        ?: getFirst(limit)

    private fun getPrevious(limit: Int, prev: Int) = prepareResponse(
        feedRepository.findBetween(prev - (limit - 1), prev + 1),
        limit,
        prev - limit,
        prev + 1
    )

    private fun getNext(limit: Int, next: Int) = prepareResponse(
        feedRepository.findBetween(next, next + limit),
        limit,
        next - 1,
        next + limit
    )

    private fun getFirst(limit: Int) = prepareResponse(
        feedRepository.findBetween(0, limit),
        limit,
        -1,
        limit
    )

    private fun prepareResponse(
        items: List<String>,
        limit: Int,
        prev: Int,
        next: Int
    ): FeedResponse {
        val hasPrev = prev > 0
        val hasNext = items.size > limit

        return FeedResponse(
            items = if (hasNext) items.dropLast(1) else items,
            navigation = Navigation(
                prev = if (hasPrev) prev else -1,
                next = if (hasNext) next else -1,
                hasPrev = hasPrev,
                hasNext = hasNext
            )
        )
    }
}
