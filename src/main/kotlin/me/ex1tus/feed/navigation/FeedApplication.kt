package me.ex1tus.feed.navigation

import me.ex1tus.feed.navigation.config.property.RedisProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RedisProperties::class)
class FeedApplication

fun main(args: Array<String>) {
	runApplication<FeedApplication>(*args)
}