package com.mcstaralliance.godofwealth.listener;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.util.RewardPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalTime;


public class PlayerJoinListener implements Listener {
    private final FileConfiguration config = GodOfWealth.getInstance().getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        LocalTime now = LocalTime.now();
        if (config.getBoolean("reward-completed-today")) {
            return;
        }
        if (now.getHour() > config.getInt("reward-after") && now.getHour() < config.getInt("selection.time")) {
            Player player = event.getPlayer();
            if (player.getName().equalsIgnoreCase(config.getString("lucky-player"))) {
                RewardPlayer.rewardAllPlayers();
                config.set("lucky-player", null);
                GodOfWealth.getInstance().saveConfig();

            }
        }
    }
}
