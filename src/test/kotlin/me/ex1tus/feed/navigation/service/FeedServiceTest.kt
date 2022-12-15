package me.ex1tus.feed.navigation.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import me.ex1tus.feed.navigation.domain.Navigation
import me.ex1tus.feed.navigation.repository.FeedRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class FeedServiceTest {

    @InjectMockKs
    private lateinit var service: FeedService

    @MockK
    private lateinit var repository: FeedRepository

    @Test
    fun `should return first page`() {
        // Given
        val feed = (0..10).map { "value_$it" }
        every { repository.findBetween(0, 10) } returns feed

        // When
        val response = service.getFeed(10, null, null)

        // Then
        assertIterableEquals(feed.dropLast(1), response.items)
        assertEquals(
            Navigation(
                prev = -1,
                next = 10,
                hasPrev = false,
                hasNext = true
            ), response.navigation
        )
    }

    @Test
    fun `should return next page`() {
        // Given
        val feed = (0..10).map { "value_$it" }
        every { repository.findBetween(10, 20) } returns feed

        // When
        val response = service.getFeed(10, null, 10)

        // Then
        assertIterableEquals(feed.dropLast(1), response.items)
        assertEquals(
            Navigation(
                prev = 9,
                next = 20,
                hasPrev = true,
                hasNext = true
            ), response.navigation
        )
    }

    @Test
    fun `should return previous page`() {
        // Given
        val feed = (0..10).map { "value_$it" }
        every { repository.findBetween(10, 20) } returns feed

        // When
        val response = service.getFeed(10, 19, null)

        // Then
        assertIterableEquals(feed.dropLast(1), response.items)
        assertEquals(
            Navigation(
                prev = 9,
                next = 20,
                hasPrev = true,
                hasNext = true
            ), response.navigation
        )
    }

    @Test
    fun `should return last page`() {
        // Given
        val feed = (0..9).map { "value_$it" }
        every { repository.findBetween(90, 100) } returns feed

        // When
        val response = service.getFeed(10, null, 90)

        // Then
        assertIterableEquals(feed, response.items)
        assertEquals(
            Navigation(
                prev = 89,
                next = -1,
                hasPrev = true,
                hasNext = false
            ), response.navigation
        )
    }

    @Test
    fun `should return whole feed`() {
        // Given
        val feed = (0..99).map { "value_$it" }
        every { repository.findBetween(0, 100) } returns feed

        // When
        val response = service.getFeed(100, null, null)

        // Then
        assertIterableEquals(feed, response.items)
        assertEquals(
            Navigation(
                prev = -1,
                next = -1,
                hasPrev = false,
                hasNext = false
            ), response.navigation
        )
    }

    @Test
    fun `should return whole feed with extra large limit`() {
        // Given
        val feed = (0..99).map { "value_$it" }
        every { repository.findBetween(0, 10000) } returns feed

        // When
        val response = service.getFeed(10000, null, null)

        // Then
        assertIterableEquals(feed, response.items)
        assertEquals(
            Navigation(
                prev = -1,
                next = -1,
                hasPrev = false,
                hasNext = false
            ), response.navigation
        )
    }
}
