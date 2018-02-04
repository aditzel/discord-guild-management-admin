package com.allanditzel.os.discord.bot.guildmanagement.admin.controller.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminIndexController.class);

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public AdminIndexController() {
    }

    @RequestMapping
    public String index(Model model, OAuth2AuthenticationToken authentication) {

        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        LOGGER.info("Principal name: {}", authorizedClient.getPrincipalName());
        LOGGER.info("Details: {}", authentication.getPrincipal().getAttributes());

//        LOGGER.info("User details service user: {}", userDetailsService.loadUserByUsername(authorizedClient.getPrincipalName()));

        return "/admin/index";
    }
}
