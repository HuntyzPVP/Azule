package org.Azule.commands;

import org.Azule.handlers.io.Config;
import org.Azule.utilites.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHealth implements CommandExecutor {
    private final Config config;

    public SetHealth(Config config) {
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
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        switch (args.length) {
            case 2:
                Player target = Bukkit.getPlayer(args[0]); // Sets "target" to the player mentioned in argument 0

                if (!(sender.hasPermission("Azule.Command.sethealth_target") || sender.hasPermission("Azule.*"))) { // Checks if the player has permission to use this command
                    sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
                    return true;
                }

                if (target == null) { // Checks if the player mentioned in argument 0 is valid
                    sender.sendMessage(Utils.chatColor(config.getString("general.messages.invalid_target")));
                    return true;
                }

                /**
                 * This try catch statement is used to see if argument 1 is a valid number and not another data type
                 */

                try {
                    if (target.getHealth() == Double.parseDouble(args[1])) { // Checks if the player already has the desired health
                        sender.sendMessage(Utils.chatColor(config.getString("sethealth.messages.health_target_already")
                                .replace("%target%", target.getName()).replace("%new_health%", args[1])));
                        return true;
                    }
                } catch (NumberFormatException numberFormatException) {
                    sender.sendMessage(Utils.chatColor(config.getString("general.exceptions.numberformatexception")));
                    return true;
                }

                if (Double.parseDouble(args[1]) > 20 || Double.parseDouble(args[1]) < 1) { // Minecraft health ranges from 1-20 therefore, it would cause an error if you went out of bounds
                    sender.sendMessage(Utils.chatColor(config.getString("sethealth.messages.number_out_of_range")));
                    return true;
                }

                target.setHealth(Double.parseDouble(args[1])); // Sets health
                target.sendMessage(Utils.chatColor(config.getString("sethealth.messages.health_set") // Sends a message letting the target know there health has been updated
                        .replace("%new_health%", args[1])));

                if (!target.equals(sender)) // If the target is not equal to the sender, then it lets the sender know they have successfully updated the targets health
                    sender.sendMessage(Utils.chatColor(config.getString("sethealth.messages.health_target_set")
                            .replace("%target%", target.getName()).replace("%new_health%", args[1])));
                return true;
            case 1:
                if (!(sender instanceof Player)) { // Checks if the player is console, if so doesn't allow it to change it's health because console obviously isn't a player
                    sender.sendMessage(Utils.chatColor(config.getString("sethealth.messages.usage")));
                    return true;
                }

                if (!(sender.hasPermission("Azule.Command.sethealth_target") || sender.hasPermission("Azule.*"))) { // Checks to see if the sender has permission
                    sender.sendMessage(Utils.chatColor(config.getString("general.messages.no_permission")));
                    return true;
                }

                Player player = (Player) sender;

                /**
                 * This try catch statement is used to see if argument 1 is a valid number and not another data type
                 */

                try {
                    if (player.getHealth() == Double.parseDouble(args[0])) { // Checks if the player already has the desired health
                        sender.sendMessage(Utils.chatColor(config.getString("sethealth.messages.health_already")
                                .replace("%new_health%", args[0])));
                        return true;
                    }
                } catch (NumberFormatException numberFormatException) {
                    sender.sendMessage(Utils.chatColor(config.getString("general.exceptions.numberformatexception")));
                    return true;
                }

                if (Double.parseDouble(args[0]) > 20 || Double.parseDouble(args[0]) < 1) { // Minecraft health ranges from 1-20 therefore, it would cause an error if you went out of bounds
                    sender.sendMessage(Utils.chatColor(config.getString("sethealth.messages.number_out_of_range")));
                    return true;
                }

                player.setHealth(Double.parseDouble(args[0])); // Sets health
                player.sendMessage(Utils.chatColor(config.getString("sethealth.messages.health_set") // Sends a message letting the sender know there health has been updated
                        .replace("%new_health%", args[0])));
                return true;
            default:
                sender.sendMessage(Utils.chatColor(config.getString("sethealth.messages.usage"))); // If arguments are wrong, it lets the sender know the correct usage
                return true;
        }
    }
}
