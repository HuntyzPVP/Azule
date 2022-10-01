package org.Azule.utilites;

import org.bukkit.ChatColor;

public class Utils {

    public static String chatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
