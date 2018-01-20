package com.allanditzel.os.discord.bot.guildmanagement.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.util.stream.Collectors.joining;

@SpringBootApplication
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Autowired
    private RedisServer redisServer;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class).web(true).run(args);
	}

    @PostConstruct
    public void start() {

        LOGGER.info("Starting redis...");
        if (!redisServer.isActive()) redisServer.start();
        LOGGER.info("Redis listening on ports: {}", redisServer.ports().stream()
                .map(Object::toString).collect(joining(",")));
    }

    @PreDestroy
    public void stop() {

        LOGGER.info("Shutting down redis...");
        redisServer.stop();
        LOGGER.info("Done shutting down redis.");
    }
}
