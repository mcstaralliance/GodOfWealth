package com.mcstaralliance.godofwealth.task;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.util.RewardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CheckRunnable extends BukkitRunnable {
    private final FileConfiguration config = GodOfWealth.getInstance().getConfig();

    @Override
    public void run() {

        LocalTime now = LocalTime.now();
        if (now.getHour() == 0) {
            config.set("selection.hasCompletedToday", false);
            config.set("lucky-player", null);
            GodOfWealth.getInstance().saveConfig();
            return;
        }
        if (config.getBoolean("selection.hasCompletedToday")) {
            return;
        }
        if (now.getHour() > config.getInt("reward-after") && now.getHour() < config.getInt("selection.time")) {
            if (Bukkit.getPlayer(UUID.fromString(config.getString("lucky-player"))).isOnline()) {
                RewardUtil.rewardAllPlayers();
                config.set("lucky-player", null);
                config.set("lucky-player-real-name", null);
                GodOfWealth.getInstance().saveConfig();
                return;
            }
        }
        if (now.getHour() == config.getInt(("selection.time"))) {
            Random random = new Random();
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            int randomNumber = random.nextInt(players.size());
            while (players.get(randomNumber).isOp()) {
                randomNumber = random.nextInt(players.size());
            }
            config.set("selection.hasCompletedToday", true);
            config.set("lucky-player", players.get(randomNumber).getUniqueId());
            config.set("lucky-player-real-name", players.get(randomNumber).getName());
            GodOfWealth.getInstance().saveConfig();
            String messageToBroadcast = ChatColor.translateAlternateColorCodes('&', config.getString("lang.broadcast-selected-player").replaceAll("%player%", players.get(randomNumber).getName()));
            Bukkit.broadcastMessage(messageToBroadcast);
        }

    }
}
