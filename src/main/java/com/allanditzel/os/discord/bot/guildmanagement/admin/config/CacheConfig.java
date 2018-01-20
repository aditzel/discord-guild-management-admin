package com.allanditzel.os.discord.bot.guildmanagement.admin.config;

import com.allanditzel.os.discord.bot.guildmanagement.admin.model.DiscordUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Protocol;
import redis.embedded.RedisServer;

@Configuration
public class CacheConfig {

    @Autowired
    JedisConnectionFactory connectionFactory;

    @Bean
    public RedisServer redisServer() {
        RedisServer.builder().reset();

        return RedisServer.builder().port(Protocol.DEFAULT_PORT).build();
    }

    @Bean
    public RedisTemplate<String, DiscordUser> redisTemplate() {
        RedisTemplate<String, DiscordUser> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
