package org.Azule.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class VanishHandler {

    private final List<Player> vanished = new ArrayList<>();

    public void vanishOn(Player player) {
        if (isVanished(player)) return;
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (!(onlinePlayer.hasPermission("Azule.Command.vanish") || (onlinePlayer.hasPermission("Azule.*"))))
                onlinePlayer.hidePlayer(player); });
        vanished.add(player);
    }

    public void vanishOff(Player player) {
        vanished.remove(player);
    }

    public boolean isVanished(Player player) {
        return vanished.contains(player);
    }
}
