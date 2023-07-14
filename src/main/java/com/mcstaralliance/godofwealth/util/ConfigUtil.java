package com.mcstaralliance.godofwealth.util;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ConfigUtil {
    private static final GodOfWealth plugin = GodOfWealth.getInstance();
    public static void checkConfig() {
        boolean selectEarly = plugin.getConfig().getInt("selection.time") <= plugin.getConfig().getInt("reward-after");
        boolean selectInMidnight= plugin.getConfig().getInt("selection.time") == 0;

        if (selectEarly) {
            plugin.getLogger().severe(StringConst.TIME_EARLY);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
        if (selectInMidnight) {
            plugin.getLogger().severe(StringConst.TIME_IS_MIDNIGHT);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public static void clearSelectionStatus() {
        plugin.getConfig().set("selection.hasCompletedToday", false);
        plugin.saveConfig();
    }

    public static void clearData() {
        plugin.getConfig().set("selection.hasCompletedToday", false);
        plugin.getConfig().set("lucky-player", null);
        plugin.getConfig().set("lucky-player-real-name", null);
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

