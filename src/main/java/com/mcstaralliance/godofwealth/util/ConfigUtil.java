package com.mcstaralliance.godofwealth.util;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ConfigUtil {
    private static final GodOfWealth plugin = GodOfWealth.getInstance();
    public static void checkConfig() {
        boolean selectionEarly = plugin.getConfig().getInt("selection.time") <= plugin.getConfig().getInt("reward-after");
        boolean selectionTimeIsZero = plugin.getConfig().getInt("selection.time") == 0;

        if (selectionEarly) {
            plugin.getLogger().severe(StringConst.TIME_EARLY);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
        if (selectionTimeIsZero) {
            plugin.getLogger().severe(StringConst.TIME_IS_MIDNIGHT);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public static void clearData() {
        plugin.getConfig().set("selection.hasCompletedToday", false);
        plugin.getConfig().set("lucky-player", "");
        plugin.getConfig().set("lucky-player-real-name", "");
        plugin.saveConfig();
    }

    public static void saveData(Player player) {
        plugin.getConfig().set("selection.hasCompletedToday", true);
        // mark if selection of today has completed.
        plugin.getConfig().set("lucky-player", player.getUniqueId().toString());
        plugin.getConfig().set("lucky-player-real-name", player.getName());
        plugin.saveConfig();
    }

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}

