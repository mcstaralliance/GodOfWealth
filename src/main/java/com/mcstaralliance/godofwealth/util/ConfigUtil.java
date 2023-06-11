package com.mcstaralliance.godofwealth.util;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ConfigUtil {
    private static final FileConfiguration config = GodOfWealth.getInstance().getConfig();

    public static void checkConfig() {
        boolean selectionEarlierThanReward = config.getInt("selection.time") <= config.getInt("reward-after");
        boolean selectionTimeIsZero = config.getInt("selection.time") == 0;

        if (selectionEarlierThanReward) {
            GodOfWealth.getInstance().getLogger().severe(StringConst.TIME_EARLY);
            GodOfWealth.getInstance().getServer().getPluginManager().disablePlugin(GodOfWealth.getInstance());
        }
        if (selectionTimeIsZero) {
            GodOfWealth.getInstance().getLogger().severe(StringConst.TIME_IS_ZERO);
            GodOfWealth.getInstance().getServer().getPluginManager().disablePlugin(GodOfWealth.getInstance());
        }
    }

    public static void clearData() {
        config.set("selection.hasCompletedToday", false);
        config.set("lucky-player", null);
        config.set("lucky-player-real-name", null);
        GodOfWealth.getInstance().saveConfig();
    }

    public static void saveData(Player player) {
        config.set("selection.hasCompletedToday", true);
        // mark if selection of today has completed.
        config.set("lucky-player", player.getUniqueId());
        config.set("lucky-player-real-name", player.getName());
        GodOfWealth.getInstance().saveConfig();
    }

    public static void finishReward() {
        config.set("lucky-player", null);
        config.set("lucky-player-real-name", null);
        GodOfWealth.getInstance().saveConfig();
    }
}

