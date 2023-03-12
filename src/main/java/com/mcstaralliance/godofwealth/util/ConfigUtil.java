package com.mcstaralliance.godofwealth.util;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {
    private static final FileConfiguration config = GodOfWealth.getInstance().getConfig();

    public static void checkConfig() {
        if (config.getInt("selection.time") <= config.getInt("reward-after")) {
            GodOfWealth.getInstance().getLogger().severe("selection time is earlier than reward time");
            GodOfWealth.getInstance().getServer().getPluginManager().disablePlugin(GodOfWealth.getInstance());
        }
    }
}
