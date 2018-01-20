package com.allanditzel.os.discord.bot.guildmanagement.admin.security;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;
import java.util.Map;

public class PrincipalHelper {

    private static final String DISCORD_USER_ID = "id";

    public static Long getDiscordUserIdFromPrincipal(Principal principal) {
        Long discordId = null;

        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)principal;
            Map details = (Map)oAuth2Authentication.getUserAuthentication().getDetails();
            discordId = Long.valueOf((String)details.get(DISCORD_USER_ID));
        }

        return discordId;
    }
}
