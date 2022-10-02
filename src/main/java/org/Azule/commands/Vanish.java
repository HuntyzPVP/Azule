package org.Azule.commands;

import org.Azule.handlers.VanishHandler;
import org.Azule.handlers.io.Config;
import org.Azule.utilites.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish implements CommandExecutor {

    private final VanishHandler handler;
    private final Config config;

    public Vanish(Config config, VanishHandler handler) {
        this.config = config;
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

            if (!(sender.hasPermission("Azule.Command.vanish_target") || sender.hasPermission("Azule.*"))) { // Checks if the sender has permission to use this command
                sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
                return true;
            }

            if (target == null) { // Checks if the player mentioned in argument 0 is valid
                sender.sendMessage(Utils.chatColor(config.getString("general.messages.invalid_target")));
                return true;
            }

            if (handler.isVanished(target)) { // Checks if the target is already vanished
                handler.vanishOff(target);
                target.sendMessage(Utils.chatColor(config.getString("vanish.messages.vanish_off")
                        .replace("%target%", target.getName())));

                if (!target.equals(sender)) { // Checks if the target is not themselves, if it is, it will not send them a message saying they have disabled vanished for the target
                    sender.sendMessage(Utils.chatColor(config.getString("vanish.messages.vanish_target_off")
                            .replace("%target%", target.getName())));
                    return true;
                }
                return true;
            }

            handler.vanishOn(target);
            target.sendMessage(Utils.chatColor(config.getString("vanish.messages.vanish_on"))); // Sends the target a message letting them know they are now vanished

            if (!target.equals(sender)) { // Checks if the target is not themselves, if it is, it will not send them a message saying they have enabled vanish for the target
                sender.sendMessage(Utils.chatColor(config.getString("vanish.messages.vanish_target_on")
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
        if (!(sender.hasPermission("Azule.Command.fly") || sender.hasPermission("Azule.*"))) { // Checks if the sender has permission to run the command
            sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
            return true;
        }

        if (handler.isVanished(player)) { // Checks if the player is already vanished
            handler.vanishOff(player);
            sender.sendMessage(Utils.chatColor(config.getString("vanish.messages.vanish_off")
                    .replace("%target%", player.getName())));
            return true;
        }

        handler.vanishOn(player);
        player.sendMessage(Utils.chatColor(config.getString("vanish.messages.vanish_on"))); // Sends the sender a message letting them know they are now vanished
        return true;
    }
}
