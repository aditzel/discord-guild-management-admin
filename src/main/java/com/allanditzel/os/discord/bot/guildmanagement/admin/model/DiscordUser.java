package com.allanditzel.os.discord.bot.guildmanagement.admin.model;

import java.io.Serializable;
import java.util.List;

public class DiscordUser implements Serializable {

    private long discordId;
    private String discordUsername;
    private List<DiscordUserGuildMembership> guildMemberships;

    protected DiscordUser() {}

    public DiscordUser(long discordId, String discordUsername, List<DiscordUserGuildMembership> guildMemberships) {
        this.discordId = discordId;
        this.discordUsername = discordUsername;
        this.guildMemberships = guildMemberships;
    }

    public long getDiscordId() {
        return discordId;
    }

    public String getDiscordUsername() {
        return discordUsername;
    }

    @Override
    public String toString() {
        return "DiscordUser{" +
                "discordId=" + discordId +
                ", discordUsername='" + discordUsername + '\'' +
                '}';
    }
}
