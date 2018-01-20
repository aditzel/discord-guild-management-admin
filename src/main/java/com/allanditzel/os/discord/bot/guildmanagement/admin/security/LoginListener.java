package com.allanditzel.os.discord.bot.guildmanagement.admin.security;

import com.allanditzel.os.discord.bot.guildmanagement.admin.model.DiscordUser;
import com.allanditzel.os.discord.bot.guildmanagement.admin.model.DiscordUserGuildMembership;
import com.allanditzel.os.discord.bot.guildmanagement.admin.repo.DiscordUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginListener.class);

    private static final String DISCORD_USER_ID = "id";
    private static final String DISCORD_USERNAME = "username";
    private static final String DISCORD_USERNAME_DISCRIMINATOR = "discriminator";

    private DiscordUserRepository discordUserRepository;
    private OAuth2RestTemplate restTemplate;
    private String userGuildsUrl;

    private LoginListener() {}

    @Autowired
    public LoginListener(DiscordUserRepository discordUserRepository, OAuth2RestTemplate restTemplate, @Value("${discord.user.guildsUrl}") String userGuildsUrl) {
        this.discordUserRepository = discordUserRepository;
        this.restTemplate = restTemplate;
        this.userGuildsUrl = userGuildsUrl;
    }

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {

        Map details = (Map)((OAuth2Authentication) event.getAuthentication()).getUserAuthentication().getDetails();
        Long discordUserId = Long.valueOf((String)details.get(DISCORD_USER_ID));
        String discordUsername = details.get(DISCORD_USERNAME) + "#" + details.get(DISCORD_USERNAME_DISCRIMINATOR);

        ResponseEntity<List<DiscordUserGuildMembership>> guildDataResponse = restTemplate.exchange(userGuildsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<DiscordUserGuildMembership>>() {});
        List<DiscordUserGuildMembership> userGuildMemberships = guildDataResponse.getBody();

        DiscordUser discordUser = new DiscordUser(discordUserId, discordUsername, userGuildMemberships);

        discordUserRepository.save(discordUser);
    }
}
