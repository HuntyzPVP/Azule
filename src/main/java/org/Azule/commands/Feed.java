package org.Azule.commands;

import org.Azule.handlers.io.Config;
import org.Azule.utilites.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feed implements CommandExecutor {

    private final Config config;

    public Feed(Config config) {
        this.config = config;
    }

    @Override
    /**
     * @param sender - The sender who ran the command. (PLayer or Console)
     * @param command - A command object.
     * @param s - Label for the command. (If you run a command such as /gmc, 'gmc' is the label)
     * @param args args Is the arguments passed as the command split by a space. (/ban [Player] 30d Hacking), args would be
     *             [Player, 30d, Hacking]
     */

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);

            if (!(sender.hasPermission("Azule.Command.feed_target") || sender.hasPermission("Azule.*"))) { // Checks if the sender has permission to use this command
                sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
                return true;
            }

            if (target == null) { // Checks if the player mentioned in argument 0 is valid
                sender.sendMessage(Utils.chatColor(config.getString("general.messages.invalid_target")));
                return true;
            }

            if (target.getFoodLevel() >= 20) { // Checks if the players food level is already max
                sender.sendMessage(Utils.chatColor(config.getString("feed.messages.food_target_max")
                        .replace("%target%", target.getName())));
                return true;
            }

            target.setFoodLevel(20); // Sets food level to max
            target .setSaturation(10); // Sets saturation level to max
            target.sendMessage(Utils.chatColor(config.getString("feed.messages.fed"))); // Sends the target a message letting them know there feed level has been updated

            if (!target.equals(sender)) { // Checks if the target is not themselves, if it is, it will not send them a message saying they have fed the target
                sender.sendMessage(Utils.chatColor(config.getString("feed.messages.fed_target")
                        .replace("%target%", target.getName())));
                return true;
            }
            return true;
        }
        // If no arguments are provided

        if (!(sender instanceof Player)) { // Checks if the console is the sender
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.target_required")));
            return true;
        }

        Player player = (Player) sender;
        if (!(sender.hasPermission("Azule.Command.feed") || sender.hasPermission("Azule.*"))) { // Checks if the sender has permission to run the command
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
            return true;
        }

        if (player.getFoodLevel() >= 20) { // Checks if the senders food level isn't already max
            sender.sendMessage(Utils.chatColor(config.getString("feed.messages.food_max")));
            return true;
        }

        player.setFoodLevel(20); // Sets food level to max
        player.setSaturation(10); // Sets saturation level to max
        player.sendMessage(Utils.chatColor(config.getString("feed.messages.fed"))); // Sends the sender a message letting them know they have been fed
        return true;
    }
}
