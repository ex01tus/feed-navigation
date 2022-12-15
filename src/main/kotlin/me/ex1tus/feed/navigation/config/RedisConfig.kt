package me.ex1tus.feed.navigation.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import me.ex1tus.feed.navigation.config.property.RedisProperties
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.Jedis
import redis.embedded.RedisServer

private val log = KotlinLogging.logger {}

@Configuration
class RedisConfig(
    private val redisProperties: RedisProperties
) {
    lateinit var server: RedisServer
    lateinit var jedis: Jedis

    @Bean
    fun jedis() = jedis

    @PostConstruct
    fun start() {
        log.info { "Starting Redis server with port: ${redisProperties.port} (http)" }
        server = RedisServer(redisProperties.port)
        server.start()
        jedis = Jedis()
        warmUp()
    }

    @PreDestroy
    fun tearDown() {
        log.info { "Stopping Redis server" }
        jedis.close()
        server.stop()
    }

    private fun warmUp() {
        log.info { "Generating test data" }
        (0 until redisProperties.feedLength)
            .forEach { jedis.zadd(redisProperties.feedKey, it.toDouble(), "value_$it") }
    }
}
