package me.ex1tus.feed.navigation.web.rest

import jakarta.validation.constraints.Positive
import me.ex1tus.feed.navigation.domain.FeedResponse
import me.ex1tus.feed.navigation.exception.InvalidRequestException
import me.ex1tus.feed.navigation.service.FeedService
import mu.KotlinLogging
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@Validated
@RestController
@RequestMapping("/feed")
class FeedResource(
    private val feedService: FeedService
) {

    @GetMapping
    fun getFeed(
        @RequestParam @Positive limit: Int,
        @RequestParam(required = false) @Positive prev: Int?,
        @RequestParam(required = false) @Positive next: Int?
    ): FeedResponse {
        log.info { "-> [GET /feed] limit=$limit, next=$next, prev=$prev" }

        if (areBothExist(next, prev)) {
            throw InvalidRequestException("getFeed: next and prev must not be specified simultaneously")
        }

        return feedService.getFeed(limit, prev, next)
            .also { log.info { "<- [GET /feed] response=$it" } }
    }

    private fun areBothExist(next: Int?, prev: Int?) = next != null && prev != null
}
