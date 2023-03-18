package com.mcstaralliance.godofwealth.task;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.util.ConfigUtil;
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


    public void saveSelectionData(Player player) {
        config.set("selection.hasCompletedToday", true);
        config.set("lucky-player", player.getUniqueId());
        config.set("lucky-player-real-name", player.getName());
        GodOfWealth.getInstance().saveConfig();
    }

    private void broadcastSelectedMessage(Player player) {
        String lang = config.getString("lang.broadcast-selected-player").replaceAll("%player%", player.getName());
        String message = ChatColor.translateAlternateColorCodes('&', lang);
        Bukkit.broadcastMessage(message);
    }

    private void selectLuckyPlayer() {
        Random random = new Random();
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        int randomNumber = random.nextInt(players.size());
        Player player = players.get(randomNumber);
        saveSelectionData(player);
        broadcastSelectedMessage(player);
    }

    @Override
    public void run() {
        LocalTime now = LocalTime.now();
        boolean hasCompletedToday = config.getBoolean("selection.hasCompletedToday");
        boolean tomorrowComes = now.getHour() == 0;
        boolean isDuringRewardTime = now.getHour() > config.getInt("reward-after") && now.getHour() < config.getInt("selection.time");
        boolean isSelectionTime = now.getHour() == config.getInt(("selection.time"));
        boolean luckyPlayerIsOnline;
        Player player = Bukkit.getPlayer(UUID.fromString(config.getString("lucky-player")));

        if (tomorrowComes) {
            ConfigUtil.clearData();
            return;
        }
        if (hasCompletedToday) {
            return;
        }
        if (isDuringRewardTime) {
            if (player == null) {
                return;
            }
            luckyPlayerIsOnline = player.isOnline();

            if (luckyPlayerIsOnline) {
                RewardUtil.rewardAllPlayers();
                ConfigUtil.finishReward();
                return;
            }
        }
        if (isSelectionTime) {
            selectLuckyPlayer();
        }

    }
}
