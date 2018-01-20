package com.allanditzel.os.discord.bot.guildmanagement.admin.controller.web.admin;

import com.allanditzel.os.discord.bot.guildmanagement.admin.repo.DiscordUserRepository;
import com.allanditzel.os.discord.bot.guildmanagement.admin.security.PrincipalHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminIndexController.class);

    private DiscordUserRepository discordUserRepository;

    private AdminIndexController() {}

    @Autowired
    public AdminIndexController(OAuth2RestTemplate restTemplate, DiscordUserRepository discordUserRepository) {
        this.discordUserRepository = discordUserRepository;
    }

    @RequestMapping
    public String index(Principal principal, Model model) {

        Long discordUserId = PrincipalHelper.getDiscordUserIdFromPrincipal(principal);

        return "/admin/index";
    }
}
