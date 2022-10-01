package org.Azule;

import org.Azule.commands.GameModeA;
import org.Azule.commands.GameModeC;
import org.Azule.commands.GameModeS;
import org.Azule.commands.GameModeSP;
import org.Azule.handlers.GameModeHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private GameModeHandler gameModeHandler;

    @Override
    // Method ran upon the plugin being enabled
    public void onEnable() {
        registerHandlers();
        registerCommands();
    }

    @Override
    // Method ran upon the plugin being disabled
    public void onDisable() {

    }

    private void registerHandlers() {
        gameModeHandler = new GameModeHandler();
    }

    private void registerCommands() {
        getCommand("gmc").setExecutor(new GameModeC(gameModeHandler));
        getCommand("gms").setExecutor(new GameModeS(gameModeHandler));
        getCommand("gmsp").setExecutor(new GameModeSP(gameModeHandler));
        getCommand("gma").setExecutor(new GameModeA(gameModeHandler));
    }

}
