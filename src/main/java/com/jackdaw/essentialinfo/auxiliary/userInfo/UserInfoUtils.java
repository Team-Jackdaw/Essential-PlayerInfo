package com.jackdaw.essentialinfo.auxiliary.userInfo;

import com.velocitypowered.api.proxy.Player;
import org.slf4j.Logger;

import java.io.*;
import java.util.HashMap;

/**
 * A serializer used to get the remembered information from the files.
 *
 * <p>
 * Read and Write the data file with server information, each file just remember one player information.
 *
 */
public final class UserInfoUtils {
    private final Logger logger;
    private final File theFile;
    private final UserInfo userInfo;

    public UserInfoUtils(File workingDirectory, Logger logger, Player player) {
        this.logger = logger;
        this.userInfo = new UserInfo(player.getUsername(), player.getUniqueId().toString(), false, null);
        this.theFile = new File(workingDirectory, player.getUniqueId().toString() + ".yml");
        readOrInitialize();
    }

    // read or initialize the user data file
    public void readOrInitialize() {
        if (!theFile.exists()) {
            writeFile();
            return;
        }
        try {
            HashMap userData = YamlUtils.readFile(theFile);
            this.userInfo.setDefaultMode((Boolean) userData.get("defaultMode"));
            this.userInfo.setServer((String) userData.get("server"));
        } catch (FileNotFoundException e) {
            logger.error("RememberMe: Can't open the user data file.");
        }
    }


    /**
     * Confirm and write the file as the UserInfo class record.
     */
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
            userData.put("defaultMode", this.userInfo.getDefaultMde());
            userData.put("server", this.userInfo.getServer());
            YamlUtils.writeFile(theFile, userData);
        } catch (IOException e) {
            this.logger.error("RememberMe: Can't write the user data file.");
        }
    }

    /**
     * Get User information from data file.
     * @return User information.
     */
    public UserInfo getUserData() {
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
     * @param defaultMode Default mode.
     */
    public void setDefaultMode(boolean defaultMode) {
        this.userInfo.setDefaultMode(defaultMode);
        writeFile();
    }
}
