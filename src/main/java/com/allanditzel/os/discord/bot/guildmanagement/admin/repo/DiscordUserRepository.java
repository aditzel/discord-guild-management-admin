package com.allanditzel.os.discord.bot.guildmanagement.admin.repo;

import com.allanditzel.os.discord.bot.guildmanagement.admin.model.DiscordUser;

public interface DiscordUserRepository {

    void save(DiscordUser user);

    void update(DiscordUser user);

    DiscordUser findById(Long id);

    void deleteById(Long id);
}
