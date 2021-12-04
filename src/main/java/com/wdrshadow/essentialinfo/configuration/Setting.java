package com.wdrshadow.essentialinfo.configuration;

import com.moandjiezana.toml.Toml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Setting {
    private final File dataFolder;
    private final File file;

    private final boolean tabListEnabled;
    private final boolean messageEnabled;
    private final boolean pingListEnabled;

    public Setting(File dataFolder){
        this.dataFolder = dataFolder;
        this.file = new File(this.dataFolder, "config.toml");

        saveDefaultConfig();
        Toml toml = loadConfig();

        this.tabListEnabled = toml.getBoolean("tabList.enabled");
        this.messageEnabled = toml.getBoolean("message.enabled");
        this.pingListEnabled = toml.getBoolean("pingList.enabled");
    }

    private void saveDefaultConfig() {
        if (!dataFolder.exists()) dataFolder.mkdir();
        if (file.exists()) return;

        try (InputStream in = Setting.class.getResourceAsStream("/config.toml")) {
            assert in != null;
            Files.copy(in, file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getConfigFile() {
        return new File(dataFolder, "config.toml");
    }

    private Toml loadConfig() {
        return new Toml().read(getConfigFile());
    }

    public boolean isTabListEnabled(){
        return tabListEnabled;
    }

    public boolean isMessageEnabled(){
        return messageEnabled;
    }

    public boolean isPingListEnabled(){
        return pingListEnabled;
    }

}
