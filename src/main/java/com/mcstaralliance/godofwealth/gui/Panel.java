package com.mcstaralliance.godofwealth.gui;

import com.mcstaralliance.godofwealth.GodOfWealth;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Panel implements InventoryHolder {

    private final FileConfiguration config = GodOfWealth.getInstance().getConfig();
    private final String title = ChatColor.translateAlternateColorCodes('&', config.getString("lang.menu-title"));
    private final Inventory inv = Bukkit.createInventory(this, 3 * 9, title);

    public Panel() {
        setBorder();
        setInfoButton();

    }

    public void setBorder() {
        String borderGlassPlainName = config.getString("lang.border-glass-name");
        String borderGlassName = ChatColor.translateAlternateColorCodes('&', borderGlassPlainName);
        ItemStack borderGlass = createButton(Material.GRAY_STAINED_GLASS, borderGlassName);
        arrangeBorder(borderGlass);

    }

    public void arrangeBorder(ItemStack border) {
        for (int i = 0; i < 27; i++) {
            ItemStack itemStack;
            if (i <= 8 || i >= 18 || i % 9 == 0 || i % 9 == 8) {
                itemStack = border;
            } else {
                itemStack = null;
            }
            inv.setItem(i, itemStack);
        }
    }

    public void setInfoButton() {
        String headPlainName = config.getString("lang.head-name");
        String headName = ChatColor.translateAlternateColorCodes('&', headPlainName);
        ItemStack head = createButton(Material.PLAYER_HEAD, headName);
        ItemMeta headMeta = head.getItemMeta();
        Player player = Bukkit.getPlayer(UUID.fromString(config.getString("lucky-player")));
        List<String> lore = config.getStringList("lang.info-lore").stream()
                .map(s -> s.replace('&', ChatColor.COLOR_CHAR))
                .map(s -> s.replaceAll("%gow_player%", config.getString("lucky-player-real-name")))
                .map(s -> PlaceholderAPI.setPlaceholders(player, s))
                .collect(Collectors.toList());
        headMeta.setLore(lore);
        inv.setItem(15, head);
    }

    public ItemStack createButton(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);

        return item;
    }

    public Inventory getInventory() {
        return inv;
    }

}
