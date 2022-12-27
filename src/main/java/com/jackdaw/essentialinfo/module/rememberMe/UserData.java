package com.jackdaw.essentialinfo.module.rememberMe;

public class UserData {
    private String name;
    private String uuid;
    private boolean defaultMode;
    private String server;

    public UserData(String name, String uuid, boolean defaultMode, String server){
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
     * @return boolean i.e. true for "remember the default server that was set by player.", false for "remember the last server when the player left last time."
     */
    public boolean getDefaultMde() {
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
    public void setDefaultMode(boolean defaultMode) {
        this.defaultMode = defaultMode;
    }

    /**
     * Set the player name from the data file.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the player uuid from the data file.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Set the server name from the data file.
     */
    public void setServer(String server) {
        this.server = server;
    }
}
