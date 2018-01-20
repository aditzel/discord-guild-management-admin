package com.allanditzel.os.discord.bot.guildmanagement.admin.model;

import java.io.Serializable;

public class DiscordUserGuildMembership implements Serializable {

    private boolean owner;
    private String permissions;
    private String icon;
    private String id;
    private String name;

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
