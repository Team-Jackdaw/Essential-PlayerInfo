package com.jackdaw.essentialinfo.module.rememberMe;

import com.velocitypowered.api.proxy.Player;
import org.slf4j.Logger;

import java.io.*;
import java.util.HashMap;

/**
 * A parser used to get the remembered information from the files.
 *
 * <p>
 * Read and Write the data file with server information, each file just remember one player information.
 *
 * @author WDRshadow
 */
public class UserInfoParser {
    private final Logger logger;
    private final File theFile;

    private UserData userData;

    public UserInfoParser(File workingDirectory, Logger logger, Player player) {
        this.logger = logger;
        this.userData = new UserData(player.getUsername(), player.getUniqueId().toString(), false, null);
        this.theFile = new File(workingDirectory, player.getUniqueId().toString() + ".yml");
        readOrInitialize();
    }

    // read or create Files.
    public void readOrInitialize() {
        if (!theFile.exists()) {
            writeFile();
            return;
        }
        try {
            HashMap userData = YamlUtils.readFile(theFile);
            this.userData.setDefaultMode((Boolean) userData.get("defaultMode"));
            this.userData.setServer((String) userData.get("server"));
        } catch (FileNotFoundException e) {
            logger.error("RememberMe: Can't open the user data file.");
        }
    }


    // confirm and write the data to file
    private void writeFile() {
        try {
            if (!theFile.exists()) {
                if (!theFile.createNewFile()) {
                    return;
                }
            }
            HashMap userData = new HashMap();
            userData.put("name", this.userData.getName());
            userData.put("uuid", this.userData.getUuid());
            userData.put("defaultMode", this.userData.getDefaultMde());
            userData.put("server", this.userData.getServer());
            YamlUtils.writeFile(theFile, userData);
        } catch (IOException e) {
            this.logger.error("RememberMe: Can't write the user data file.");
        }
    }

    public UserData getUserData(){
        return userData;
    }

    public void setUserServer(String server){
        this.userData.setServer(server);
        writeFile();
    }
}
