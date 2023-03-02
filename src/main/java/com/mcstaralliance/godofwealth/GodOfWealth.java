package com.mcstaralliance.godofwealth;

import com.mcstaralliance.godofwealth.command.MyCommand;
import com.mcstaralliance.godofwealth.listener.PlayerJoinListener;
import com.mcstaralliance.godofwealth.task.CheckRunnable;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class GodOfWealth extends JavaPlugin {
    private static GodOfWealth instance;
    private static Economy econ = null;
    private static PlayerPointsAPI playerPoints;

    public static GodOfWealth getInstance() {
        return instance;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public void setupPlayerPoints() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            playerPoints = PlayerPoints.getInstance().getAPI();
        } else {
            getLogger().info("PlayerPoints is not installed.");
        }

    }

    public static PlayerPointsAPI getPlayerPoints() {
        return playerPoints;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(instance);
            return;
        }
        setupPlayerPoints();
        Bukkit.getPluginCommand("gow").setExecutor(new MyCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), instance);
        CheckRunnable checkRunnable = new CheckRunnable();
        checkRunnable.runTaskTimer(instance, 0L, 6000L);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

}
