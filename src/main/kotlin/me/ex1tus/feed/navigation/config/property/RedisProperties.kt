package me.ex1tus.feed.navigation.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("redis")
data class RedisProperties(
    val port: Int,
    val feedKey: String,
    val feedLength: Int
)
