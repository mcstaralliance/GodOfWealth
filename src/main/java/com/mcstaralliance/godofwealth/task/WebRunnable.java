package com.mcstaralliance.godofwealth.task;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.util.StringConst;
import org.bukkit.scheduler.BukkitRunnable;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.server.Server;

public class WebRunnable extends BukkitRunnable {
    private static final GodOfWealth plugin = GodOfWealth.getInstance();
    @Override
    public void run() {
        Server server = GodOfWealth.getJettyServer();
        try {
            server.start();
            HttpGenerator.setJettyVersion(String.format("%s/%s", plugin.getDescription().getName(),
                    plugin.getDescription().getVersion()));
        } catch (Exception e) {
            plugin.getLogger().severe(StringConst.BIND_PORT_FAIL);
            e.printStackTrace();
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}
