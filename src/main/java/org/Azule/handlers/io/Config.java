package org.Azule.handlers.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config extends YamlConfiguration {

    private File file;
    private final JavaPlugin plugin;

    /**
     * Creates a yaml file based off a default configuration; def.
     * @param plugin    Plugin instance.
     * @param directory Save directory of file.
     * @param def       Default file configuration.
     * @param name      Name of new yaml configuration.
     */
    public Config(JavaPlugin plugin, File directory, String def, String name) {
        this.plugin = plugin;
        setUp(directory, def, name.endsWith(".yml") ? name : name + ".yml");
    }

    /**
     * Creates an empty yaml file.
     * @param plugin    Plugin instance.
     * @param directory Save directory of file.
     * @param name      Name of new yaml configuration.
     */
    public Config(JavaPlugin plugin, File directory, String name) {
        this.plugin = plugin;
        setUp(directory, name.endsWith(".yml") ? name : name + ".yml");
    }

    public void delete() {
        file.delete();
    }

    /**
     * Refreshes the yaml's default configuration (clears any changes made)
     * @param def Default file name stored in resource file.
     */
    public void copyDefaults(String def) {
        load(); // New file
        InputStream is = plugin.getResource(def);
        if (is != null) { // If is isn't invalid
            try {
                super.load(new InputStreamReader(is)); // Copy defaults
            } catch (IOException | InvalidConfigurationException exception) {
                exception.printStackTrace();
            }
        }
        save(); // Save changes
    }

    /**
     * Creates a blank yaml file
     */

    private boolean setUp(File directory, String name) {
        directory.mkdirs();
        file = new File(directory, name);
        if (!file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException io) {
                io.printStackTrace();
            }
            return true; // File directory doesn't exist
        }
        load();
        save();
        return false; // File directory exists
    }

    private void setUp(File path, String def, String name) {
        if (setUp(path, name)) copyDefaults(def);
    }

    /**
     * Generates a new file.
     */
    private void load() {
        try {
            super.load(file);
        } catch (IOException | InvalidConfigurationException io) {
            io.printStackTrace();
        }
    }

    /**
     * Saves changes made to the file.
     */
    private void save() {
        try {
            super.save(file);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
