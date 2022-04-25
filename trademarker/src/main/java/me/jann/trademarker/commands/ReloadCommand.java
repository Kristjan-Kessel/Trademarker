package me.jann.trademarker.commands;

import me.jann.trademarker.Trademarker;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    Trademarker main;
    public ReloadCommand(Trademarker main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("trademarker.reload")){
            sender.sendMessage(ChatColor.GOLD+"Reloading config...");
            main.reloadConfig();
            sender.sendMessage(ChatColor.GREEN+"Config reloaded!");
            if(!main.getConfig().getString("lang.trademark_format").contains("%player%")){
                sender.sendMessage(ChatColor.RED+"Trademark format doesn't have a %player% placeholder! add it back or new trademarks won't work.");
            }
        }
        return true;
    }
}
