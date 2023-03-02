package com.mcstaralliance.godofwealth.listener;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinListener implements Listener {
    private final FileConfiguration config = GodOfWealth.getInstance().getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equalsIgnoreCase(config.getString("lucky-player"))) {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            for (Player playerToReward : players) {
                rewardPlayer(playerToReward, "money");
            }
            config.set("lucky-player", null);
            GodOfWealth.getInstance().saveConfig();

        }
    }

    public void rewardPlayer(Player player, String type) {
        switch (type) {
            case "money":
                GodOfWealth.getEconomy().depositPlayer(player, config.getInt("reward-amount"));
                break;
            case "points":
                GodOfWealth.getPlayerPoints().give(player.getUniqueId(), config.getInt("reward-amount"));
            default:
                break;
        }
    }
}
