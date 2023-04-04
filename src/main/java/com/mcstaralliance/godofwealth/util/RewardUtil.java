package com.mcstaralliance.godofwealth.util;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RewardUtil {
    private static final FileConfiguration config = GodOfWealth.getInstance().getConfig();

    public static void rewardPlayer(Player player, String type) {
        switch (type) {
            case "money":
                GodOfWealth.getEconomy().depositPlayer(player, config.getInt("reward.amount"));
                break;
            case "points":
                GodOfWealth.getPlayerPoints().give(player.getUniqueId(), config.getInt("reward.amount"));
                break;
            default:
                break;
        }
    }

    public static void rewardAllPlayers() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            rewardPlayer(player, config.getString("reward.type"));
            player.sendMessage(config.getString("lang.reward-message"));
        }
    }
}
