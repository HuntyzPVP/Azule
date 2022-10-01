package org.Azule.commands;

import org.Azule.handlers.GameModeHandler;
import org.Azule.utilites.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeA implements CommandExecutor {

    private final GameModeHandler handler;

    public GameModeA(GameModeHandler handler) {
        this.handler = handler;
    }

    @Override
    /**
     * @param sender - The sender who ran the command. (PLayer or Console)
     * @param command - A command object.
     * @param s - Label for the command. (If you run a command such as /gmc, 'gmc' is the label)
     * @param args args Is the arguments passed as the command split by a space. (/ban [Player] 30d Hacking), args would be
     *             [Player, 30d, Hacking]
     */
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            handler.updateGameMode(sender, target, GameMode.ADVENTURE);
            return true;
        }
        handler.updateGameMode(sender, GameMode.ADVENTURE);
        return true;
    }
}

