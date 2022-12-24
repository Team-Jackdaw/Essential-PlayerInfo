package com.jackdaw.essentialinfo.module.rememberMe;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.velocitypowered.api.proxy.Player;
import org.slf4j.Logger;

import java.io.*;

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

    private String name;
    private String uuid;
    private boolean defaultMode;
    private String server;

    public UserInfoParser(File workingDirectory, Logger logger, Player player) {
        this.logger = logger;
        this.name = player.getUsername();
        this.uuid = player.getUniqueId().toString();
        this.theFile = new File(workingDirectory, player.getUniqueId().toString() + ".json");
        try {
            if(!workingDirectory.exists()){
                if(!workingDirectory.mkdir()){
                    logger.error("Can't make the new folder.");
                }
            }
            readFile();
        } catch (IOException e) {
            this.logger.error("Can't open file.");
        }
    }

    // read the data file of one player
    private void readFile() throws IOException {
        if (!theFile.exists()) {
            this.defaultMode = false;
            this.server = null;
            writeFile();
            return;
        }
        JsonReader reader = new JsonReader(new FileReader(theFile));
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("uuid")) {
                this.uuid = reader.nextString();
            }
            if (name.equals("name")) {
                this.name = reader.nextString();
            }
            if (name.equals("defaultMode")) {
                this.defaultMode = reader.nextBoolean();
            }
            if (name.equals("server")) {
                this.server = reader.nextString();
            }
        }
        reader.endObject();
    }

    // confirm and write the data to file
    private void writeFile() {
        try {
            if (!theFile.exists()) {
                if(!theFile.createNewFile()){
                    return;
                }
            }
            JsonWriter writer = new JsonWriter(new FileWriter(theFile));
            writer.beginObject();
            writer.name("uuid").value(uuid);
            writer.name("name").value(name);
            writer.name("defaultMode").value(defaultMode);
            writer.name("server").value(server);
            writer.endObject();
        } catch (IOException e) {
            this.logger.error("Can't write the file.");
        }
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
    public boolean isDefaultOn() {
        return defaultMode;
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
        writeFile();
    }

    /**
     * Set the player name from the data file.
     */
    public void setName(String name) {
        this.name = name;
        writeFile();
    }

    /**
     * Set the player uuid from the data file.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
        writeFile();
    }

    /**
     * Set the server name from the data file.
     */
    public void setServer(String server) {
        this.server = server;
        writeFile();
    }
}
