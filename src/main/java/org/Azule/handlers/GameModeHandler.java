package org.Azule.handlers;

import org.Azule.utilites.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeHandler {

    // Function used if there is a target

    /**
     * @param sender - The sender who ran the command. (PLayer or Console)
     * @param target - The player whos gamemode you are updating.
     * @param gm - The gamemode you are updating the target too.
     */

    public void updateGameMode(CommandSender sender, Player target, GameMode gm) {

        // Checks if the sender has permission to run the command

        if (!(sender.hasPermission("Server.Command.gm_target") || sender.hasPermission("Server.*"))) {
            sender.sendMessage(Utils.chatColor("&bAzule&8 | &cYou are not permitted to run this command."));
            return;
        }

        // Checks if target is invalid or offline

        if (target == null) {
            sender.sendMessage(Utils.chatColor("&bAzule&8 | &cThe target you have entered is invalid."));
            return;
        }

        // Checks if target is already in the desired GameMode

        if (target.getGameMode().equals(gm)) {
            sender.sendMessage(Utils.chatColor("&bAzule&8 | &cThe target's gamemode is already " + gm.name()));
            return;
        }

        // Updates the targets GameMode and sends the target a message

        target.setGameMode(gm);
        target.sendMessage(Utils.chatColor("&bAzule&8 | &7Your gamemode has been updated to " + gm.name()));

        // Checks if the target is not themselves, if it is, it will not send them a message saying they have updated the targets GameMode

        if (!target.equals(sender))
            sender.sendMessage(Utils.chatColor("&bAzule&8 | &7" + target.getName() + " gamemode has been updated to " + gm.name()));
    }

    // Function used if there is no target

    /**
     * @param sender - The sender who ran the command. (PLayer or Console)
     * @param gm - The gamemode the sender is updating themselves too.
     */

    public void updateGameMode(CommandSender sender, GameMode gm) {

        // Checks if the sender is the console

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chatColor("&bAzule&8 | &cMust enter a target!"));
            return;
        }

        // Checks if the sender has permission to run the command

        Player player = (Player) sender;
        if (!(player.hasPermission("Server.Command.gm") || player.hasPermission("Server.*"))) {
            sender.sendMessage(Utils.chatColor("&bAzule&8 | &cYou are not permitted to run this command."));
            return;
        }

        // Checks if sender is already in the desired GameMode

        if (player.getGameMode().equals(gm)) {
            sender.sendMessage(Utils.chatColor("&bAzule&8 | &cYou are already in gamemode " + gm.name()));
            return;
        }

        // Updates the senders GameMode and sends them a message saying it has been updated

        player.setGameMode(gm);
        player.sendMessage(Utils.chatColor("&bAzule&8 | &7Your gamemode has been updated to " + gm.name()));
    }
}
