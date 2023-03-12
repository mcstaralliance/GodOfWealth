package com.mcstaralliance.godofwealth.listener;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final FileConfiguration config = GodOfWealth.getInstance().getConfig();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(config.getString("lang.menu-name"))) {
            event.setCancelled(true);
        }
    }
}
