package com.mcstaralliance.godofwealth.gui;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Panel {
    private static Inventory inv = null;

    public Panel() {
        // TODO
        FileConfiguration config = GodOfWealth.getInstance().getConfig();
        String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getString("lang.menu-name"));
        Inventory gui = Bukkit.createInventory(null, 3 * 9, inventoryName);
        inv = gui;

        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS);
        gui.addItem();
    }

    public Inventory getInventory() {
        return inv;
    }
}
