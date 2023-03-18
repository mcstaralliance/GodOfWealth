package com.mcstaralliance.godofwealth.listener;

import com.mcstaralliance.godofwealth.gui.Panel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDragListener implements Listener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {

        if (event.getInventory().getHolder() instanceof Panel) {
            event.setCancelled(true);
        }
    }
}
