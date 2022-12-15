package me.ex1tus.feed.navigation.web.rest

import io.mockk.every
import io.mockk.mockk
import me.ex1tus.feed.navigation.domain.ErrorCode
import me.ex1tus.feed.navigation.domain.FeedResponse
import me.ex1tus.feed.navigation.domain.Navigation
import me.ex1tus.feed.navigation.service.FeedService
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
@ExtendWith(SpringExtension::class)
class FeedResourceTest {

    @TestConfiguration
    class TestConfig {

        @Bean
        fun service() = mockk<FeedService>()
    }

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var service: FeedService

    @Test
    fun `should return valid json response`() {
        // Given
        val expected = FeedResponse(
            items = listOf("1", "2", "3"),
            navigation = Navigation(
                prev = 0,
                next = -1,
                hasPrev = true,
                hasNext = false
            )
        )

        every { service.getFeed(10, null, null) } returns expected

        // When & Then
        mvc.perform(get("/feed").param("limit", "10"))
            .andExpect(status().isOk)
            .andExpect(header().string("content-type", "application/json"))
            .andExpect(jsonPath("$.items", hasSize<Any>(3)))
            .andExpect(jsonPath("$.navigation.prev", `is`(0)))
            .andExpect(jsonPath("$.navigation.next", `is`(-1)))
            .andExpect(jsonPath("$.navigation.hasPrev", `is`(true)))
            .andExpect(jsonPath("$.navigation.hasNext", `is`(false)))
    }

    @Test
    fun `should return bad request response on validation exception`() {
        mvc.perform(get("/feed").param("limit", "-1"))
            .andExpect(status().isBadRequest)
            .andExpect(header().string("content-type", "application/json"))
            .andExpect(jsonPath("$.code", `is`(ErrorCode.CONSTRAINT_VIOLATION.name)))
            .andExpect(jsonPath("$.description", `is`("getFeed.limit: must be greater than 0")))
    }
}
