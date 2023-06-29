package com.mcstaralliance.godofwealth.util;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ConfigUtil {

    public static void checkConfig() {
        boolean selectionEarly = GodOfWealth.getInstance().getConfig().getInt("selection.time") <= GodOfWealth.getInstance().getConfig().getInt("reward-after");
        boolean selectionTimeIsZero = GodOfWealth.getInstance().getConfig().getInt("selection.time") == 0;

        if (selectionEarly) {
            GodOfWealth.getInstance().getLogger().severe(StringConst.TIME_EARLY);
            GodOfWealth.getInstance().getServer().getPluginManager().disablePlugin(GodOfWealth.getInstance());
        }
        if (selectionTimeIsZero) {
            GodOfWealth.getInstance().getLogger().severe(StringConst.TIME_IS_ZERO);
            GodOfWealth.getInstance().getServer().getPluginManager().disablePlugin(GodOfWealth.getInstance());
        }
    }

    public static void clearData() {
        GodOfWealth.getInstance().getConfig().set("selection.hasCompletedToday", false);
        GodOfWealth.getInstance().getConfig().set("lucky-player", "");
        GodOfWealth.getInstance().getConfig().set("lucky-player-real-name", "");
        GodOfWealth.getInstance().saveConfig();
    }

    public static void saveData(Player player) {
        GodOfWealth.getInstance().getConfig().set("selection.hasCompletedToday", true);
        // mark if selection of today has completed.
        GodOfWealth.getInstance().getConfig().set("lucky-player", player.getUniqueId().toString());
        GodOfWealth.getInstance().getConfig().set("lucky-player-real-name", player.getName());
        GodOfWealth.getInstance().saveConfig();
    }

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}

