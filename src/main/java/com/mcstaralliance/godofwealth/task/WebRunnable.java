package com.mcstaralliance.godofwealth.task;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.util.StringConst;
import org.bukkit.scheduler.BukkitRunnable;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.server.Server;

public class WebRunnable extends BukkitRunnable {
    @Override
    public void run() {
        Server server = GodOfWealth.getJettyServer();
        try {
            server.start();
            HttpGenerator.setJettyVersion(String.format("%s/%s", GodOfWealth.getInstance().getDescription().getName(),
                    GodOfWealth.getInstance().getDescription().getVersion()));
        } catch (Exception e) {
            GodOfWealth.getInstance().getLogger().severe(StringConst.BIND_PORT_FAIL);
            e.printStackTrace();
            GodOfWealth.getInstance().getServer().getPluginManager().disablePlugin(GodOfWealth.getInstance());
        }
    }
}
