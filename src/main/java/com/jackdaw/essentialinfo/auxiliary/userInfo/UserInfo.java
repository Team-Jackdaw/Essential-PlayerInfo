package com.jackdaw.essentialinfo.auxiliary.userInfo;

public class UserInfo {
    private final String name;
    private final String uuid;
    private String defaultMode;
    private String server;

    /**
     * A user's information class. To initialize the class, you need the parameters below.
     *
     * @param name        User's name.
     * @param uuid        User's uuid.
     * @param defaultMode User's default mode.
     * @param server      User's default server.
     */
    public UserInfo(String name, String uuid, String defaultMode, String server) {
        this.name = name;
        this.uuid = uuid;
        this.defaultMode = defaultMode;
        this.server = server;
    }

    /**
     * Get the player name from the data file.
     *
     * @return the String, i.e. name of player.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the remembered mode of player from the data file.
     *
     * @return String i.e. "preset" for "remember the default server that was set by player.", "last" for "remember the last server when the player left last time."
     */
    public String getDefaultMde() {
        return this.defaultMode;
    }

    /**
     * Get the player uuid from the data file.
     *
     * @return the String, i.e. uuid of player.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Get the server name from the data file.
     *
     * @return the String, i.e. the server name.
     */
    public String getServer() {
        return server;
    }

    /**
     * Set the remembered mode of player from the data file.
     */
    public void setDefaultMode(String defaultMode) {
        this.defaultMode = defaultMode;
    }

    /**
     * Set the server name from the data file.
     */
    public void setServer(String server) {
        this.server = server;
    }
}
