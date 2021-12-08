package com.wdrshadow.essentialinfo.configuration;

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
 * @version 0.0.3
 */
public class SettingManager {
    private final Logger logger;
    private final File workingDirectory;
    private final File configFile;

    private boolean tabListEnabled;
    private boolean messageEnabled;
    private boolean pingListEnabled;
    private boolean connectionTipsEnabled;

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
        this.pingListEnabled = toml.getBoolean("pingList.enabled");
        this.connectionTipsEnabled = toml.getBoolean("connectionTips.enabled");
    }

    private void saveDefaultConfig() throws IOException {
        if (!workingDirectory.exists()) {
            boolean aBoolean = workingDirectory.mkdir();
            if (!aBoolean) logger.warn("Could Not make a new config.toml file.");
        }
        if (configFile.exists()) {
            // Do nothing
        } else {
            InputStream in = SettingManager.class.getResourceAsStream("/config.toml");
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

}
