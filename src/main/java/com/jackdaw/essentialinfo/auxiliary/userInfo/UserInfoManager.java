package com.jackdaw.essentialinfo.auxiliary.userInfo;

import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * A serializer used to read or write the user information from the files.
 *
 * <p>Read or Write the data file with server information, each file just remember one player information.</p>
 */
public final class UserInfoManager {
    private final Logger logger;
    private final File theFile;
    private final UserInfo userInfo;

    public UserInfoManager(File workingDirectory, Logger logger, @NotNull Player player) {
        this.logger = logger;
        this.userInfo = new UserInfo(player.getUsername(), player.getUniqueId().toString(), "last", null);
        this.theFile = new File(workingDirectory, player.getUniqueId().toString() + ".yml");
        readOrInitialize();
    }

    // read or initialize the user data file
    private void readOrInitialize() {
        if (!theFile.exists()) {
            writeFile();
            return;
        }
        try {
            HashMap userData = YamlUtils.readFile(theFile);
            this.userInfo.setDefaultMode((String) userData.get("defaultMode"));
            this.userInfo.setServer((String) userData.get("server"));
        } catch (FileNotFoundException e) {
            logger.error("RememberMe: Can't open the user data file.");
        }
    }


    // Confirm and write the file as the UserInfo class record.
    private void writeFile() {
        try {
            if (!theFile.exists()) {
                if (!theFile.createNewFile()) {
                    return;
                }
            }
            HashMap userData = new HashMap();
            userData.put("name", this.userInfo.getName());
            userData.put("uuid", this.userInfo.getUuid());
            userData.put("defaultMode", this.userInfo.getDefaultMode());
            userData.put("server", this.userInfo.getServer());
            YamlUtils.writeFile(theFile, userData);
        } catch (IOException e) {
            this.logger.error("RememberMe: Can't write the user data file.");
        }
    }

    /**
     * Get User information from data file.
     *
     * @return User information.
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * Set user's default server.
     * @param server Default server.
     */
    public void setUserServer(String server) {
        this.userInfo.setServer(server);
        writeFile();
    }

    /**
     * Set user's default mode.
     *
     * @param defaultMode Default mode.
     */
    public void setDefaultMode(String defaultMode) {
        this.userInfo.setDefaultMode(defaultMode);
        writeFile();
    }
}
