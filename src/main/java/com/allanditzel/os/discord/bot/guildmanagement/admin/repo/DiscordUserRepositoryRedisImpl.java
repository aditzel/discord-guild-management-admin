package com.allanditzel.os.discord.bot.guildmanagement.admin.repo;

import com.allanditzel.os.discord.bot.guildmanagement.admin.model.DiscordUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class DiscordUserRepositoryRedisImpl implements DiscordUserRepository {

    private static final String KEY = "DiscordUser";

    private RedisTemplate<String, DiscordUser> redisTemplate;
    private HashOperations<String, Long, DiscordUser> hashOps;

    private DiscordUserRepositoryRedisImpl() {}

    @Autowired
    public DiscordUserRepositoryRedisImpl(RedisTemplate<String, DiscordUser> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(DiscordUser user) {
        hashOps.put(KEY, user.getDiscordId(), user);
    }

    @Override
    public void update(DiscordUser user) {
        hashOps.put(KEY, user.getDiscordId(), user);
    }

    @Override
    public DiscordUser findById(Long id) {
        return hashOps.get(KEY, id);
    }

    @Override
    public void deleteById(Long id) {
        hashOps.delete(KEY, id);
    }
}
