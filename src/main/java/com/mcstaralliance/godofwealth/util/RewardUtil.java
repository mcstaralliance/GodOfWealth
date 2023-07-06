package com.mcstaralliance.godofwealth.util;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RewardUtil {
    private static final GodOfWealth plugin = GodOfWealth.getInstance();
    public static void rewardPlayer(Player player, String type) {
        switch (type) {
            case "money":
                GodOfWealth.getEconomy().depositPlayer(player, plugin.getConfig().getInt("reward.amount"));
                break;
            case "points":
                GodOfWealth.getPlayerPoints().give(player.getUniqueId(), plugin.getConfig().getInt("reward.amount"));
                break;
            default:
                break;
        }
    }

    public static void rewardAllPlayers() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (players.isEmpty()) {
            return;
        }
        for (Player player : players) {
            rewardPlayer(player, plugin.getConfig().getString("reward.type"));
            player.sendMessage(ConfigUtil.color(plugin.getConfig().getString("lang.reward-message")));
        }
    }

}
