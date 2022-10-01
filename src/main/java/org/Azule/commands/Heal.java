package org.Azule.commands;

import org.Azule.handlers.io.Config;
import org.Azule.utilites.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

    private final Config config;

    public Heal(Config config) {
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

            if (!(sender.hasPermission("Azule.Command.heal_target") || sender.hasPermission("Azule.*"))) { // Checks if the sender has permission to use this ommand
                sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
                return true;
            }

            if (target == null) { // Checks if the player mentioned in argument 0 is valid
                sender.sendMessage(Utils.chatColor(config.getString("general.messages.invalid_target")));
                return true;
            }

            if (target.getHealth() >= 20) { // Checks if the players health is already max
                sender.sendMessage(Utils.chatColor(config.getString("heal.messages.health_target_max")
                        .replace("%target%", target.getName())));
                return true;
            }

            target.setHealth(20.0); // Sets health to max
            target.sendMessage(Utils.chatColor(config.getString("heal.messages.healed"))); // Sends the target a message letting them know there health ahs been updated

            if (!target.equals(sender)) { // Checks if the target is not themselves, if it is, it will not send them a message saying they have healed the target
                sender.sendMessage(Utils.chatColor(config.getString("heal.messages.healed_target")
                        .replace("%target%", target.getName())));
                return true;
            }
        }
        // If no arguments are provided

        if (!(sender instanceof Player)) { // Checks if the console is the sender
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.target_required")));
            return true;
        }

        Player player = (Player) sender;
        if (!(sender.hasPermission("Azule.Command.heal_target") || sender.hasPermission("Azule.*"))) { // Checks if the sender has permission to run the command
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
            return true;
        }

        if (player.getHealth() >= 20) { // Checks if the senders health isn't already max
            sender.sendMessage(Utils.chatColor(config.getString("heal.messages.health_max")));
            return true;
        }

        player.setHealth(20.0); // Sets health to max
        player.sendMessage(Utils.chatColor(config.getString("heal.messages.healed"))); // Sends the sender a message letting them know they have been healed
        return true;
    }
}
