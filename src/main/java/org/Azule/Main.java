package org.Azule;

import org.Azule.commands.*;
import org.Azule.handlers.GameModeHandler;
import org.Azule.handlers.io.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Config config;

    private GameModeHandler gameModeHandler;

    @Override
    // Method ran upon the plugin being enabled
    public void onEnable() {
        registerFiles();
        registerHandlers();
        registerCommands();
    }

    private void registerFiles() {
        config = new Config(this, getDataFolder(), "config.yml", "config.yml");
    }

    private void registerHandlers() {
        gameModeHandler = new GameModeHandler(config);
    }

    private void registerCommands() {
        getCommand("gmc").setExecutor(new GameModeC(gameModeHandler));
        getCommand("gms").setExecutor(new GameModeS(gameModeHandler));
        getCommand("gmsp").setExecutor(new GameModeSP(gameModeHandler));
        getCommand("gma").setExecutor(new GameModeA(gameModeHandler));
        getCommand("heal").setExecutor(new Heal(config));
        getCommand("sethealth").setExecutor(new SetHealth(config));
        getCommand("feed").setExecutor(new Feed(config));
        getCommand("fly").setExecutor(new Fly(config));
    }

}
