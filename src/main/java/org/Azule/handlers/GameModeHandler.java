package org.Azule.handlers;

import org.Azule.handlers.io.Config;
import org.Azule.utilites.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeHandler {

    private final Config config;

    public GameModeHandler(Config config) {
        this.config = config;
    }

    // Function used if there is a target

    /**
     * @param sender - The sender who ran the command. (PLayer or Console)
     * @param target - The player whos gamemode you are updating.
     * @param gm - The gamemode you are updating the target too.
     */

    public void updateGameMode(CommandSender sender, Player target, GameMode gm) {

        if (!(sender.hasPermission("Azule.Command.gm_target") || sender.hasPermission("Azule.*"))) { // Checks if the sender has permission to run the command
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
            return;
        }

        if (target == null) { // Checks if the target mentioned in argument 0 is valid
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.invalid_target")));
            return;
        }

        if (target.getGameMode().equals(gm)) { // Checks to make sure the target isn't already in the desired gamemode
            sender.sendMessage(Utils.chatColor(config.getString("gamemode.messages.gamemode_target_already")
                    .replace("%target%", target.getName()).replace("%gamemode%", gm.name())));
            return;
        }

        target.setGameMode(gm); // Updates the targets gamemode
        target.sendMessage(Utils.chatColor(config.getString("gamemode.messages.gamemode_update") // Sends the target a message letting them know there gamemode has been updated
                .replace("%gamemode%", gm.name())));

        if (!target.equals(sender)) { // Checks if the target is not themselves, if it is, it will not send them a message saying they have updated the targets GameMode
            sender.sendMessage(Utils.chatColor(config.getString("gamemode.messages.gamemode_target_update")
                    .replace("%target%", target.getName()).replace("%gamemode%", gm.name())));
        }
    }

    // Function used if there is no target

    /**
     * @param sender - The sender who ran the command. (PLayer or Console)
     * @param gm - The gamemode the sender is updating themselves too.
     */

    public void updateGameMode(CommandSender sender, GameMode gm) {

        if (!(sender instanceof Player)) { // Checks if the sender is console
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.target_required")));
            return;
        }

        Player player = (Player) sender; // Makes the sender a Player object after confirmed console isn't the sender
        if (!(player.hasPermission("Azule.Command.gm") || player.hasPermission("Azule.*"))) { // Checks if the sender has permission to run the command
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
            return;
        }

        if (player.getGameMode().equals(gm)) { // Checks if the sender is already in there desired gamemode
            sender.sendMessage(Utils.chatColor(config.getString("gamemode.messages.gamemode_already")
                    .replace("%gamemode%", gm.name())));
            return;
        }

        player.setGameMode(gm); // Updates gamemode
        player.sendMessage(Utils.chatColor(config.getString("gamemode.messages.gamemode_update") // Sends the sender a message letting them know there gamemode has been updated
                .replace("%gamemode%", gm.name())));
    }
}
