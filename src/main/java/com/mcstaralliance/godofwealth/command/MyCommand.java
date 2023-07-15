package com.mcstaralliance.godofwealth.command;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.gui.Panel;
import com.mcstaralliance.godofwealth.util.StringConst;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MyCommand implements CommandExecutor {
    private static final GodOfWealth plugin = GodOfWealth.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(ChatColor.RED + StringConst.INVALID_ARGUMENTS);
            return true;
        }
        switch (args[0]) {
            case "reload":
                if (sender.hasPermission("godofwealth.reload")) {
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + StringConst.PLUGIN_RELOADED);
                } else {
                    sender.sendMessage(ChatColor.RED + StringConst.PERMISSION_DENIED);
                }
                return true;
            case "set":
                if (sender.hasPermission("godofwealth.set")) {
                    plugin.getConfig().set("lucky-player-real-name", args[1]);
                    plugin.getConfig().set("lucky-player", getPlayerUUID(args[1]).toString());
                    plugin.getConfig().set("selection.hasCompletedToday", true);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "已将 " + args[1] + " 设定为财神爷。");
                } else {
                    sender.sendMessage(ChatColor.RED + StringConst.PERMISSION_DENIED);
                }
                return true;
            case "panel":
                if (sender instanceof Player) {
                    Panel panel = new Panel();
                    Player player = (Player) sender;
                    player.openInventory(panel.getInventory());
                } else {
                    sender.sendMessage(ChatColor.RED + StringConst.PLAYER_ONLY_COMMAND);
                }
                return true;
            default:
                sender.sendMessage(ChatColor.RED + StringConst.COMMAND_NOT_EXIST);
                break;
        }
        return false;
    }

    public UUID getPlayerUUID(String playerName) {
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer.getName().equalsIgnoreCase(playerName)) {
                return offlinePlayer.getUniqueId();
            }
        }
        return null;
    }
}
