package com.mcstaralliance.godofwealth.task;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.util.ConfigUtil;
import com.mcstaralliance.godofwealth.util.RewardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CheckRunnable extends BukkitRunnable {
    private boolean hasClearedData = false;

    private void broadcastSelectedMessage(Player player) {
        String lang = GodOfWealth.getInstance().getConfig().getString("lang.broadcast-selected-player").replaceAll("%player%", player.getName());
        String message = ChatColor.translateAlternateColorCodes('&', lang);
        Bukkit.broadcastMessage(message);
    }

    private void selectLuckyPlayer() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Random random = new Random();
        int randomNumber = random.nextInt(players.size());
        Player player = players.get(randomNumber);
        ConfigUtil.saveData(player);
        broadcastSelectedMessage(player);
    }

    @Override
    public void run() {
        // in charge of selecting lucky player & rewarding.
        LocalTime now = LocalTime.now();
        boolean hasCompletedToday = GodOfWealth.getInstance().getConfig().getBoolean("selection.hasCompletedToday");
        boolean tomorrowComes = now.getHour() == 0;
        boolean isDuringRewardTime = now.getHour() > GodOfWealth.getInstance().getConfig().getInt("reward.after")
                && now.getHour() < GodOfWealth.getInstance().getConfig().getInt("selection.time");
        boolean isSelectionTime = now.getHour() == GodOfWealth.getInstance().getConfig().getInt(("selection.time"));
        OfflinePlayer player = Bukkit.getPlayer(UUID.fromString(GodOfWealth.getInstance().getConfig().getString("lucky-player")));

        if (tomorrowComes) {
            if (hasClearedData) {
                return;
            }
            ConfigUtil.clearData();
            hasClearedData = true;
            return;
        }
        if (hasCompletedToday) {
            // 阻止新的财神爷产生
            return;
        }
        if (isDuringRewardTime) {
            if (player == null) {
                return;
            }

            if (player.isOnline()) {
                RewardUtil.rewardAllPlayers();
                ConfigUtil.finishReward();
                return;
            }
        }
        if (isSelectionTime) {
            selectLuckyPlayer();
            hasClearedData = false;
        }

    }
}
