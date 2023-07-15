package com.mcstaralliance.godofwealth;

import com.mcstaralliance.godofwealth.command.MyCommand;
import com.mcstaralliance.godofwealth.listener.InventoryClickListener;
import com.mcstaralliance.godofwealth.listener.InventoryDragListener;
import com.mcstaralliance.godofwealth.task.CheckRunnable;
import com.mcstaralliance.godofwealth.task.WebRunnable;
import com.mcstaralliance.godofwealth.util.ConfigUtil;
import com.mcstaralliance.godofwealth.util.StringConst;
import com.mcstaralliance.godofwealth.web.WebErrorHandler;
import com.mcstaralliance.godofwealth.web.WebHandler;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;

public final class GodOfWealth extends JavaPlugin {
    private static GodOfWealth instance;
    private static Economy econ = null;
    private static PlayerPointsAPI playerPoints;

    private static final Server server = new Server();

    public static GodOfWealth getInstance() {
        return instance;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Server getJettyServer() {
        return server;
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

    public static void setupJetty() {
        // quoted from BotApi
        ContextHandler context = new ContextHandler("/");
        context.setHandler(new WebHandler());
        context.setErrorHandler(new WebErrorHandler());
        ServerConnector connector = new ServerConnector(server);
        connector.setAcceptQueueSize(50);
        int port = getInstance().getConfig().getInt("web.port");
        connector.setPort(port);
        connector.setIdleTimeout(5000L);
        server.addConnector(connector);
        server.setStopAtShutdown(true);
        server.setHandler(context);
        WebRunnable webRunnable = new WebRunnable();
        webRunnable.runTaskAsynchronously(getInstance());
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        ConfigUtil.checkConfig();
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(getInstance());
            return;
        }
        setupPlayerPoints();
        Bukkit.getPluginCommand("gow").setExecutor(new MyCommand());
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), getInstance());
        Bukkit.getPluginManager().registerEvents(new InventoryDragListener(), getInstance());
        CheckRunnable checkRunnable = new CheckRunnable();
        checkRunnable.runTaskTimer(getInstance(), 5L, 1200L);
        FileConfiguration config = GodOfWealth.getInstance().getConfig();
        if (config.getBoolean("web.enable")) {
            setupJetty();
        }

    }

    @Override
    public void onDisable() {
        try {
            server.stop();
        } catch (Exception e) {
            getLogger().severe(StringConst.STOP_WEB_FAIL);
            e.printStackTrace();
        }
    }

}
