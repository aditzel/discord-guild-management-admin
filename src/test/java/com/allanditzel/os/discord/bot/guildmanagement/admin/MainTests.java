package com.allanditzel.os.discord.bot.guildmanagement.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"DISCORD_CLIENT_ID:discord_client_id", "DISCORD_CLIENT_SECRET:discord_client_secret"})
public class MainTests {

	@Test
	public void contextLoads() {
	}

}
