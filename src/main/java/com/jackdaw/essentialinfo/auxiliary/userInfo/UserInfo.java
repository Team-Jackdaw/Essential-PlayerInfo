package com.jackdaw.essentialinfo.auxiliary.userInfo;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfo {
    private final String name;
    private final String uuid;
    private String defaultMode;
    private String server;
    private List<String> whiteList;

    /**
     * A user's information class. To initialize the class, you need the parameters below.
     *
     * @param name        User's name.
     * @param uuid        User's uuid.
     * @param defaultMode User's default mode.
     * @param server      User's default server.
     */
    public UserInfo(String name, String uuid, String defaultMode, String server, List<String> whiteList) {
        this.name = name;
        this.uuid = uuid;
        this.defaultMode = defaultMode;
        this.server = server;
        this.whiteList = whiteList;
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
    public String getDefaultMode() {
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
     * Get the white list from the data file.
     *
     * @return the List of white list, i.e. the server list.
     */
    public List<String> getWhiteList() {
        return whiteList;
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

    /**
     * Add a server on the list.
     */
    public void addWhiteList(String server) {
        if (this.whiteList.stream().anyMatch(w -> w.equals(server))) return;
        this.whiteList.add(server);
    }

    /**
     * Remove a server on the list.
     */
    public void removeWhiteList(String server) {
        this.whiteList.remove(whiteList.stream().filter(w -> w.equals(server)).findFirst().orElse(null));
    }

    /**
     * Set servers on the list.
     */
    public void setWhiteList(List<String> serverList) {
        this.whiteList = serverList;
    }
}
