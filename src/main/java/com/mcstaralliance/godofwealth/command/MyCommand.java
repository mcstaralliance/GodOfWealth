package com.mcstaralliance.godofwealth.command;

import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) {
            sender.sendMessage(ChatColor.RED + "参数不合法");
            return false;
        }
        switch (args[0]) {
            case "reload":
                GodOfWealth.getInstance().reloadConfig();
                return true;
            case "set-player":
                FileConfiguration config = GodOfWealth.getInstance().getConfig();
                config.set("lucky-player", args[1]);
                GodOfWealth.getInstance().saveConfig();
                return true;
            default:
                sender.sendMessage(ChatColor.RED + "你输入的指令不存在");
        }
        return false;
    }

}
