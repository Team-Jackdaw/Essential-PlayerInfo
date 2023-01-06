package com.jackdaw.essentialinfo.auxiliary.configuration;

import com.moandjiezana.toml.Toml;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Manages the config files for the modules provided by this plugin.
 *
 * <p>
 * Configure the setting if a specific module is enable or disable.
 *
 * @author WDRshadow, Lokeyli
 * @version 3.0
 */
public class SettingManager {
    private final Logger logger;
    private final File workingDirectory;
    private final File configFile;

    private static final String lastVersion = "v3.0"; // use for confirming the setting version is the same with the plugin

    private boolean tabListEnabled;
    private boolean messageEnabled;
    private boolean pingListEnabled;
    private boolean connectionTipsEnabled;
    private boolean commandToBroadcastEnabled;
    private boolean customTextEnabled;
    private boolean rememberMeEnable;
    private String connectionText;
    private String serverChangeText;
    private String disconnectionText;
    private String chatText;

    /**
     * Instantiates a new Setting manager.
     *
     * @param workingDirectory the directory to store the config file
     * @throws IOException the io exception. The client who
     *                     create the instance is responsible for this.
     */
    public SettingManager(File workingDirectory, Logger logger) throws IOException {
        this.logger = logger;
        this.workingDirectory = workingDirectory;
        this.configFile = new File(this.workingDirectory, "config.toml");
        setUp();
    }

    private void setUp() throws IOException {
        saveDefaultConfig();
        Toml toml = new Toml().read(new File(workingDirectory, "config.toml"));
        this.tabListEnabled = toml.getBoolean("tabList.enabled");
        this.messageEnabled = toml.getBoolean("message.enabled");
        this.commandToBroadcastEnabled = toml.getBoolean("message.command-to-broadcast");
        this.pingListEnabled = toml.getBoolean("pingList.enabled");
        this.connectionTipsEnabled = toml.getBoolean("connectionTips.enabled");
        this.customTextEnabled = toml.getBoolean("customText.enable");
        this.rememberMeEnable = toml.getBoolean("rememberMe.enable");
        this.connectionText = toml.getString("customText.connectionText");
        this.serverChangeText = toml.getString("customText.serverChangeText");
        this.disconnectionText = toml.getString("customText.disconnectionText");
        this.chatText = toml.getString("customText.chatText");
    }

    private void saveDefaultConfig() throws IOException {
        if (!workingDirectory.exists()) {
            boolean aBoolean = workingDirectory.mkdir();
            if (!aBoolean) logger.warn("Could Not make a new config.toml file.");
        }
        if (configFile.exists()) {
            Toml toml = new Toml().read(new File(workingDirectory, "config.toml"));
            String v;
            try {
                v = toml.getString("version.version");
                if (v.equals(lastVersion)) {
                    return;
                }
            } catch (Exception ignored) {
            }
            boolean aBoolean = configFile.delete();
            if (!aBoolean) logger.warn("Could Not delete old config file.");
        }
        newConfig();
    }

    private void newConfig() throws IOException {
        InputStream in = SettingManager.class.getResourceAsStream("/config.toml");
        try (in) {
            assert in != null;
            Files.copy(in, configFile.toPath());
        }
    }

    /**
     * Is tab list enabled.
     *
     * @return the boolean, i.e. enabled returns true; otherwise, false.
     */
    public boolean isTabListEnabled() {
        return tabListEnabled;
    }

    /**
     * Is message sending across servers enabled.
     *
     * @return the boolean, i.e. enabled returns true; otherwise, false.
     */
    public boolean isMessageEnabled() {
        return messageEnabled;
    }

    /**
     * Is user need to use command for broadcasting.
     *
     * @return the boolean, i.e. enabled returns true; otherwise, false.
     */
    public boolean isCommandToBroadcastEnabled() {
        return commandToBroadcastEnabled;
    }

    /**
     * Is ping list enabled.
     *
     * @return the boolean, i.e. enabled returns true; otherwise, false.
     */
    public boolean isPingListEnabled() {
        return pingListEnabled;
    }

    /**
     * Is connection tips enabled.
     *
     * @return the boolean, i.e. enabled returns true; otherwise, false.
     */
    public boolean isConnectionTipsEnabled() {
        return connectionTipsEnabled;
    }

    /**
     * Is custom text enabled.
     *
     * @return the boolean, i.e. enabled returns true; otherwise, false.
     */
    public boolean isCustomTextEnabled() {
        return customTextEnabled;
    }

    /**
     * Is remember my previous server enabled.
     *
     * @return the boolean, i.e. enabled returns true; otherwise, false.
     */
    public boolean isRememberMeEnabled() {
        return rememberMeEnable;
    }

    /**
     * Get Connection Text
     *
     * @return the String.
     */
    public String getConnectionText() {
        return connectionText;
    }

    /**
     * Get Server Change Text
     *
     * @return the String.
     */
    public String getServerChangeText() {
        return serverChangeText;
    }

    /**
     * Get Disconnection Text
     *
     * @return the String.
     */
    public String getDisconnectionText() {
        return disconnectionText;
    }

    /**
     * Get Chat Text
     *
     * @return the String.
     */
    public String getChatText() {
        return chatText;
    }

}
