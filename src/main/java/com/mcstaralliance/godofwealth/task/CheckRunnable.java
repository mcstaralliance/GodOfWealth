package com.mcstaralliance.godofwealth.task;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.util.RewardPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CheckRunnable extends BukkitRunnable {
    private final FileConfiguration config = GodOfWealth.getInstance().getConfig();

    @Override
    public void run() {
        if (config.getBoolean("selection.hasCompletedToday")) {
            return;
        }
        LocalTime now = LocalTime.now();
        if (now.getHour() == 0) {
            config.set("selection.hasCompletedToday", false);
            config.set("lucky-player", null);
            GodOfWealth.getInstance().saveConfig();
            return;
        }
        if (now.getHour() == config.getInt(("selection.time"))) {
            Random random = new Random();
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            int randomNumber = random.nextInt(players.size());
            while (players.get(randomNumber).isOp()) {
                randomNumber = random.nextInt(players.size());
            }
            config.set("lucky-player", players.get(randomNumber).getName());
            String messageToBroadcast = ChatColor.translateAlternateColorCodes('&', config.getString("lang.broadcast-selected-player").replaceAll("%player%", players.get(randomNumber).getName()));
            Bukkit.broadcastMessage(messageToBroadcast);
            GodOfWealth.getInstance().saveConfig();
        }
        if (now.getHour() > config.getInt("reward-after") && now.getHour() < config.getInt("selection.time")) {
            if (Bukkit.getPlayerExact(config.getString("lucky-player")).isOnline()) {
                RewardPlayer.rewardAllPlayers();
            }
        }
    }
}
