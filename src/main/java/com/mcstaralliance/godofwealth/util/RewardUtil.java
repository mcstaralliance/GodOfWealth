package com.mcstaralliance.godofwealth.util;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RewardUtil {

    public static void rewardPlayer(Player player, String type) {
        switch (type) {
            case "money":
                GodOfWealth.getEconomy().depositPlayer(player, GodOfWealth.getInstance().getConfig().getInt("reward.amount"));
                break;
            case "points":
                GodOfWealth.getPlayerPoints().give(player.getUniqueId(), GodOfWealth.getInstance().getConfig().getInt("reward.amount"));
                break;
            default:
                break;
        }
    }

    public static void rewardAllPlayers() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            rewardPlayer(player, GodOfWealth.getInstance().getConfig().getString("reward.type"));
            player.sendMessage(color(GodOfWealth.getInstance().getConfig().getString("lang.reward-message")));
        }
    }

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
