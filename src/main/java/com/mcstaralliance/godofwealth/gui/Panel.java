package com.mcstaralliance.godofwealth.gui;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Panel implements InventoryHolder {

    FileConfiguration config = GodOfWealth.getInstance().getConfig();
    String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getString("lang.menu-title"));
    private final Inventory inv = Bukkit.createInventory(this, 3 * 9, inventoryName);

    public Panel() {
        // TODO
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS);
        inv.addItem();
    }

    public Inventory getInventory() {
        return inv;
    }

}
