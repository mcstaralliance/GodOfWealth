package com.mcstaralliance.godofwealth.command;

import com.mcstaralliance.godofwealth.GodOfWealth;
import com.mcstaralliance.godofwealth.gui.Panel;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args.length > 2){
            sender.sendMessage(ChatColor.RED + "参数不合法");
            return true;
        }
        switch (args[0]) {
            case "reload":
                GodOfWealth.getInstance().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "插件已重载");
                return true;
            case "set-player":
                FileConfiguration config = GodOfWealth.getInstance().getConfig();
                config.set("lucky-player", args[1]);
                GodOfWealth.getInstance().saveConfig();
                return true;
            case "panel":
                if (sender instanceof Player) {
                    Panel panel = new Panel();
                    Player player = (Player) sender;
                    player.openInventory(panel.getInventory());
                } else {
                    sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
                }
                return true;
            default:
                sender.sendMessage(ChatColor.RED + "你输入的指令不存在");
                break;
        }
        return false;
    }

}
