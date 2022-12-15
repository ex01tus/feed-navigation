package me.ex1tus.feed.navigation.repository

import me.ex1tus.feed.navigation.config.property.RedisProperties
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import redis.clients.jedis.Jedis

private val log = KotlinLogging.logger {}

@Repository
class RedisRepository(
    private val jedis: Jedis,
    private val redisProperties: RedisProperties
) : FeedRepository {

    override fun findBetween(from: Int, to: Int): List<String> {
        log.info { "Redis: ZRANGE ${redisProperties.feedKey} $from $to" }
        return jedis.zrange(redisProperties.feedKey, from.toLong(), to.toLong()).toList()
    }
}
